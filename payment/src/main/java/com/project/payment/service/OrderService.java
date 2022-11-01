package com.project.payment.service;

import com.project.payment.domain.Order;
import com.project.payment.domain.OrderStatus;
import com.project.payment.feign.dto.LessonResponse;
import com.project.payment.dto.OrderDto;
import com.project.payment.feign.client.TrainerServiceClient;
import com.project.payment.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderService {

    public final OrderRepository orderRepository;

    private final TrainerServiceClient trainerServiceClient;

    public Order saveOrder(OrderDto orderDto, String userId){
        LessonResponse lessonResponse = trainerServiceClient.getLesson(orderDto.getLessonId());

        OrderDto dto = OrderDto.builder()
                .userId(userId)
                .lessonId(orderDto.getLessonId())
                .lessonName(lessonResponse.getLessonName())
                .lessonPrice(lessonResponse.getPrice())
                .paymentType(orderDto.getPaymentType())
                .build();
        return orderRepository.save(new Order(dto));
    }

    public Order getOrder(Long orderId){

        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<OrderDto> getOrders(String userId){

        return orderRepository.findByUserId(userId)
                .stream()
                .map(OrderDto::of)
                .collect(Collectors.toList());
    }

    public Order updateOrder(Long orderId, OrderStatus orderStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("주문번호가 존재하지 않습니다."));

        order.setOrderStatus(orderStatus);
        return orderRepository.save(order);
    }

}
