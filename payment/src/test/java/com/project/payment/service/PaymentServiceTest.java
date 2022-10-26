package com.project.payment.service;

import com.project.payment.domain.Order;
import com.project.payment.domain.OrderStatus;
import com.project.payment.domain.Payment;
import com.project.payment.dto.OrderDto;
import com.project.payment.repository.PaymentRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class PaymentServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentRepository paymentRepository;



    @Test
    public void savePaymentTest(){

        String userId = "user12";
        Long id = 1L;
        Long lessonId = 1L;
        String lessonName = "testLesson";
        Long lessonPrice = 10000L;
        String paymentType = "무통장입금";

        Order updateOrder = orderService.updateOrder(id, OrderStatus.FINISHED);
        OrderDto dto = OrderDto.builder()
                .id(id)
                .userId(userId)
                .lessonId(lessonId)
                .lessonName(lessonName)
                .lessonPrice(lessonPrice)
                .paymentType(paymentType)
                .build();

        Payment payment = paymentRepository.save(new Payment(dto));
        assertEquals(payment.getUserId(), userId);
        assertEquals(payment.getLessonId(), lessonId);
        assertEquals(payment.getLessonPrice(), lessonPrice);
        assertEquals(payment.getPaymentType(), paymentType);
        assertEquals(updateOrder.getOrderStatus().toString(), "FINISHED");
    }

    @Test
    public void cancelPaymentTest(){
        Long orderId = 1L;
        Long paymentId = 1L;

        Order updateOrder = orderService.updateOrder(orderId, OrderStatus.CANCEL);
        paymentRepository.deleteById(paymentId);

        assertEquals(updateOrder.getOrderStatus().toString(), "CANCEL");
    }

}
