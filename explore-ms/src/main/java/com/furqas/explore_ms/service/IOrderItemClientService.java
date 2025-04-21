package com.furqas.explore_ms.service;

import com.furqas.explore_ms.domains.orderitemclient.dto.GetOrderItemByNameDto;

import java.util.List;

public interface IOrderItemClientService {

    List<GetOrderItemByNameDto> getOrderItemByName(String name);

}
