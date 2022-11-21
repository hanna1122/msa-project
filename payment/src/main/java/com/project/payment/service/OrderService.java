package com.project.payment.service;

import com.project.payment.domain.Order;
import com.project.payment.domain.OrderStatus;
import com.project.payment.dto.Result;
import com.project.payment.exception.CustomException;
import com.project.payment.feign.dto.LessonResponse;
import com.project.payment.dto.OrderDto;
import com.project.payment.feign.client.TrainerServiceClient;
import com.project.payment.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    public final OrderRepository orderRepository;

    private final TrainerServiceClient trainerServiceClient;

    public Order saveOrder(OrderDto orderDto, String userId){
        Result<LessonResponse> lessonResponseResult = trainerServiceClient.getLesson(orderDto.getLessonId());
        if (Result.Code.ERROR.equals(lessonResponseResult.getCode())) {
            // error
            return null;
        }
        LessonResponse lessonResponse = lessonResponseResult.getData();

        OrderDto dto = OrderDto.builder()
                .userId(userId)
                .trainerId(lessonResponse.getTrainerId())
                .lessonId(orderDto.getLessonId())
                .lessonName(lessonResponse.getLessonName())
                .lessonPrice(lessonResponse.getPrice())
                .paymentType(orderDto.getPaymentType())
                .build();
        return orderRepository.save(new Order(dto));
    }

    public Order getOrder(Long orderId){

        return orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(HttpStatus.CONFLICT, "User not found"));
    }

    public List<OrderDto> getOrders(String userId){

        return orderRepository.findByUserId(userId)
                .stream()
                .map(OrderDto::of)
                .collect(Collectors.toList());
    }

    @Async("updateOrderAsync")
    public Order updateOrder(Long orderId, OrderStatus orderStatus){

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(HttpStatus.CONFLICT, "Order not found."));

        order.setOrderStatus(orderStatus);
        return orderRepository.save(order);

    }

}
