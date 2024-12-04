package com.example.json_view_example.mapper;

import com.example.json_view_example.domain.dto.request.UpsertOrderRequest;
import com.example.json_view_example.domain.dto.response.OrderResponse;
import com.example.json_view_example.domain.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderMapper INSTANCE = getMapper(OrderMapper.class);

    @Mapping(target = "status", expression = "java(com.example.json_view_example.domain.entity.enums.OrderStatus.valueOf(request.getStatus()))")
    Order toEntity(UpsertOrderRequest request);

    OrderResponse toResponse(Order order);
}
