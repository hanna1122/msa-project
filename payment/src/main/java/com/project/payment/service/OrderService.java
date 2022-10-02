package com.project.payment.service;

import com.project.payment.domain.Order;
import com.project.payment.domain.OrderStatus;
import com.project.payment.dto.OrderDto;
import com.project.payment.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OrderService {

    public final OrderRepository orderRepository;


    public Order saveOrder(OrderDto orderDto, String userId){

        OrderDto dto = OrderDto.builder()
                .userId(userId)
                .lessonId(orderDto.getLessonId())
                .lessonName(orderDto.getLessonName())
                .lessonPrice(orderDto.getPrice())
                .paymentType(orderDto.getPaymentType())
                .build();
        return orderRepository.save(new Order(dto));
    }

    public Order getOrder(String userId){

        return orderRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public Order updateOrder(Long orderId, OrderStatus orderStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("주문번호가 존재하지 않습니다."));

        order.setOrderStatus(orderStatus);
        return orderRepository.save(order);
    }

}
