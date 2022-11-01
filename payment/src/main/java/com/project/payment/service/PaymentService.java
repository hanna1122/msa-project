package com.project.payment.service;

import com.project.payment.domain.Order;
import com.project.payment.domain.OrderStatus;
import com.project.payment.domain.Payment;
import com.project.payment.dto.OrderDto;
import com.project.payment.feign.client.GymServiceClient;
import com.project.payment.feign.dto.LessonResponse;
import com.project.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;

@Service
@RequiredArgsConstructor
public class PaymentService {
    public final PaymentRepository paymentRepository;

    public final OrderService orderService;

    public final GymServiceClient gymServiceClient;

    @Transactional
    public Payment savePayment(OrderDto orderDto, String userId){
        Order updateOrder = orderService.updateOrder(orderDto.getId(), OrderStatus.FINISHED);
        OrderDto dto = OrderDto.builder()
                .id(orderDto.getId())
                .userId(userId)
                .lessonId(updateOrder.getLessonId())
                .lessonName(updateOrder.getLessonName())
                .lessonPrice(updateOrder.getLessonPrice())
                .paymentType(updateOrder.getPaymentType())
                .build();
        Payment payment = paymentRepository.save(new Payment(dto));
        dto.setPaymentId(payment.getId());
        gymServiceClient.saveTicket(dto, userId);
        return payment;
    }

    @Transactional
    public Order cancelPayment(OrderDto orderDto, String userId){
        paymentRepository.deleteById(orderDto.getPaymentId());
        return orderService.updateOrder(orderDto.getId(), OrderStatus.CANCEL);
    }
}
