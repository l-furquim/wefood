package com.furqas.explore_ms.service.impl;

import com.furqas.explore_ms.config.client.IOrderItemClient;
import com.furqas.explore_ms.domains.orderitemclient.dto.GetOrderItemByNameDto;
import com.furqas.explore_ms.service.IOrderItemClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OrderItemClientServiceImpl implements IOrderItemClientService {

    private final IOrderItemClient client;

    public OrderItemClientServiceImpl(IOrderItemClient client) {
        this.client = client;
    }

    @Override
    public List<GetOrderItemByNameDto> getOrderItemByName(String name) {
        try{
            var orderItem = client.getOrderItemByName(name).getBody();

            return orderItem;
        }catch (Exception e){
            log.error("Erro ao realizar requisição para o order item client: " + e.getMessage());
        }

        return null;
    }
}
