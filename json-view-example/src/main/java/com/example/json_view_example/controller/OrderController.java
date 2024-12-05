package com.example.json_view_example.controller;

import com.example.json_view_example.domain.dto.request.UpsertOrderRequest;
import com.example.json_view_example.domain.dto.response.SuccessResponse;
import com.example.json_view_example.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse create(@RequestBody UpsertOrderRequest request) {
        return orderService.create(request);
    }

}
