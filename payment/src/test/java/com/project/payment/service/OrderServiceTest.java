package com.project.payment.service;

import com.project.payment.domain.Order;
import com.project.payment.domain.OrderStatus;
import com.project.payment.dto.OrderDto;
import com.project.payment.repository.OrderRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderRepository orderRepository;


    @Test
    public void saveOrderTest(){

        String userId = "user12";
        Long lessonId = 1L;
        String lessonName = "testLesson";
        Long lessonPrice = 10000L;
        String paymentType = "무통장입금";

        OrderDto dto = OrderDto.builder()
                .userId(userId)
                .lessonId(lessonId)
                .lessonName(lessonName)
                .lessonPrice(lessonPrice)
                .paymentType(paymentType)
                .build();

        Order order = orderRepository.save(new Order(dto));
        assertEquals(order.getUserId(), userId);
        assertEquals(order.getLessonId(), lessonId);
        assertEquals(order.getLessonPrice(), lessonPrice);
        assertEquals(order.getPaymentType(), paymentType);
        assertEquals(order.getOrderStatus().toString(), "PLACED");
    }

    @Test
    public void getOrderTest(){
        String userId = "user12";
        Long lessonId = 1L;
        Long orderId = 1L;
        String lessonName = "testLesson";
        Long lessonPrice = 10000L;
        String paymentType = "무통장입금";

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("User not found"));
        assertEquals(order.getUserId(), userId);
        assertEquals(order.getLessonId(), lessonId);
        assertEquals(order.getLessonPrice(), lessonPrice);
        assertEquals(order.getPaymentType(), paymentType);
        assertEquals(order.getOrderStatus().toString(), "PLACED");
    }

    @Test
    public void getOrdersTest(){
        String userId = "user12";
        List<OrderDto> order = orderRepository.findByUserId(userId)
                .stream()
                .map(OrderDto::of)
                .collect(Collectors.toList());

        OrderDto orderDto = OrderDto.builder()
                .userId(userId)
                .lessonId(1L)
                .lessonName("testLesson")
                .lessonPrice(10000L)
                .build();

        assertThat(order).contains(orderDto);
    }

    @Test
    public void updateOrderTest(){
        Order order = orderRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("주문번호가 존재하지 않습니다."));

        order.setOrderStatus(OrderStatus.FINISHED);
        Order updateOrder = orderRepository.save(order);
        assertEquals(updateOrder.getOrderStatus().toString(), "FINISHED");
    }
}
