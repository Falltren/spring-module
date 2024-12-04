package com.example.json_view_example.domain.entity;

import com.example.json_view_example.domain.entity.enums.OrderStatus;
import com.example.json_view_example.view.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    @Builder.Default
    @JsonView(Views.UserSummary.class)
    private List<String> products = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @JsonView(Views.UserSummary.class)
    private OrderStatus status;

    @JsonView(Views.UserSummary.class)
    private BigDecimal cost;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
