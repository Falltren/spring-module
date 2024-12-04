package com.example.json_view_example.service;

import com.example.json_view_example.domain.dto.request.UpsertOrderRequest;
import com.example.json_view_example.domain.dto.response.OrderResponse;
import com.example.json_view_example.domain.entity.Order;
import com.example.json_view_example.domain.entity.User;
import com.example.json_view_example.exception.EntityNotFoundException;
import com.example.json_view_example.mapper.OrderMapper;
import com.example.json_view_example.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;

    public Order getOrderById(Long id) {
        return getOrder(id);
    }

    public OrderResponse create(UpsertOrderRequest request) {
        User user = userService.getUserById(request.getUserId());
        Order order = OrderMapper.INSTANCE.toEntity(request);
        order.setUser(user);
        return OrderMapper.INSTANCE.toResponse(orderRepository.save(order));
    }

    private Order getOrder(Long id) {
        return orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(MessageFormat.format("Order with ID: {0} not found", id)));
    }
}
