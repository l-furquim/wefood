package com.lucas.pedido_ms.services;

import com.lucas.pedido_ms.domains.orderitem.OrderItem;
import com.lucas.pedido_ms.domains.orderitem.dto.*;
import com.lucas.pedido_ms.domains.orderitem.exception.InvalidOrderItemDataException;
import com.lucas.pedido_ms.domains.orderitem.exception.OrderItemNotFoundException;
import com.lucas.pedido_ms.repositories.OrderItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemService {

    private static final Logger log = LoggerFactory.getLogger(OrderItemService.class);
    private final OrderItemRepository orderItemRepository;
    private final OrderService orderService;
    private final IRestaurantService restaurantService;
    private final S3Client s3Client;
    private static final String BUCKET = "furqas-wefood-buckets";

    public OrderItemService(
            OrderItemRepository orderItemRepository,
            OrderService orderService,
            IRestaurantService restaurantService,
            S3Client s3Client
    ) {
        this.orderItemRepository = orderItemRepository;
        this.orderService = orderService;
        this.restaurantService = restaurantService;
        this.s3Client = s3Client;
    }

    public OrderItem create(CreateOrderItemDto dto){
        if(dto.data().title() == null || dto.data().title().isEmpty() || dto.data().description() == null || dto.data().description().isEmpty() ||
            dto.data().price().compareTo(BigDecimal.ZERO) < 0 || dto.data().quantity() <= 0 || dto.data().userId() == null || dto.data().userId().isEmpty()){
            throw new InvalidOrderItemDataException("Error creating the order item: invalid data");
        }

        var restaurant = restaurantService.getById(dto.data().restaurantId());

        if(restaurant == null){
            throw new OrderItemNotFoundException("Could not found the restaurant for the order item creation");
        }

        var orderItem = new OrderItem(
                dto.data().title(),
                dto.data().description(),
                dto.data().quantity(),
                dto.data().price(),
                dto.data().restaurantId()
        );

        saveOrderItemImage(dto.image(), restaurant.getBody().id(),orderItem.getId());


//        if(data.orderId() != null){
//            var order = orderService.findById(data.orderId());
//            if(order.getUserId().equals(data.userId())){
//                throw new InvalidOrderItemDataException("Error creating the order item: cannot create a order that doesn't contains the user id");
//            }
//
//            orderItem.setOrder(order);
//        }

        return orderItemRepository.save(orderItem);
    }

    @Transactional
    public OrderItem update(UpdateOrderItemDto data){
        if(data.id() == null){
            throw new InvalidOrderItemDataException("Order item id is null");
        }

        var orderItem = orderItemRepository.findById(data.id());

        if(orderItem.isEmpty()){
            throw new OrderItemNotFoundException("Order not found");
        }

        if(data.title() != null) orderItem.get().setTitle(data.title());
        if(data.description() != null) orderItem.get().setDescription(data.description());
        if(data.price() != null) orderItem.get().setPrice(data.price());
        if(data.quantity() != null) orderItem.get().setQuantity(data.quantity());
        if(data.order() != null) orderItem.get().setOrder(data.order());

        orderItemRepository.save(orderItem.get());

        return orderItem.get();
    }

    @Transactional
    public void delete(DeleteOrderItemDto data){
        var orderItem = orderItemRepository.findById(data.id())
                .orElseThrow(() -> new OrderItemNotFoundException("Order item not found for delete"));

        orderItemRepository.delete(orderItem);
    }

    public List<OrderItem> get(){
        return orderItemRepository.findAll();
    }

    public OrderItem findById(Long id) {
        return orderItemRepository.findById(id).orElseThrow(() -> new OrderItemNotFoundException("Cannot found the order item by id"));
    }
    
    public List<FindByRestaurantDto>  findByRestaurant(Long restaurantId){
        var orderItems = this.orderItemRepository.findByRestaurantId(restaurantId);
        
        var orderItemsImagesPath = getRestaurantOrdersItemImagePath(restaurantId);
        
        List<FindByRestaurantDto> itemsWithImageList = new ArrayList<>();

        for (OrderItem orderItem : orderItems) {
            var paths = orderItemsImagesPath.stream().filter(
                    p -> p.contains(
                            String.format(
                                    "%d/images/%d",
                                    restaurantId,
                                    orderItem.getId()
                            )
                    )
            ).toList();


            if(!paths.isEmpty()){
                itemsWithImageList.add(
                        new FindByRestaurantDto(
                                orderItem,
                                paths
                        )
                );
                continue;
            }
            itemsWithImageList.add(
                    new FindByRestaurantDto(
                            orderItem,
                            new ArrayList<>()
                    )
            );
        }
        return itemsWithImageList;
    }

    private List<String> getRestaurantOrdersItemImagePath(Long restaurantId){
        List<String> imagesPath = new ArrayList<>();

        ListObjectsV2Request objectsListRequest = ListObjectsV2Request.builder()
                .bucket(BUCKET)
                .prefix(restaurantId + "/images/")
                .build();

        ListObjectsV2Response objectsListResponse = s3Client.listObjectsV2(objectsListRequest);

        List<S3Object> imagesList = objectsListResponse.contents();

        for (S3Object s3Object : imagesList) {
            imagesPath.add(s3Object.key());
        }

        return imagesPath;
    }

    private void saveOrderItemImage(MultipartFile image, Long resturantId, Long orderItemId){
        new Thread(() -> {
            try{
                var imageBytes = image.getBytes();

                PutObjectRequest put = PutObjectRequest.builder()
                        .bucket(BUCKET)
                        .key(resturantId + "/images/" + orderItemId + "/" + image.getName())
                        .build();

                s3Client.putObject(
                        put,
                        RequestBody.fromBytes(
                                imageBytes
                        )
                );

            }catch (IOException e){
                log.error("Erro ao salvar imagem do item de pedido " + e.getMessage());
            }
        });
    }
}
