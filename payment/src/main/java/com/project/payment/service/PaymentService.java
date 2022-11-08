package com.project.payment.service;

import com.project.payment.domain.Order;
import com.project.payment.domain.OrderStatus;
import com.project.payment.domain.Payment;
import com.project.payment.dto.OrderDto;
import com.project.payment.feign.client.GymServiceClient;
import com.project.payment.message.event.PerformanceSaveEvent;
import com.project.payment.message.event.TicketSaveEvent;
import com.project.payment.repository.OrderRepository;
import com.project.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {
    public final PaymentRepository paymentRepository;

    public final OrderService orderService;
    public final OrderRepository orderRepository;

    public final GymServiceClient gymServiceClient;

    private final KafkaTemplate<String, TicketSaveEvent> kafkaTicketTemplate;
    private final KafkaTemplate<String, PerformanceSaveEvent> kafkaPerformanceTemplate;

    @Transactional
    public Payment savePayment(OrderDto orderDto, String trainerId){
        Order updateOrder = orderService.getOrder(orderDto.getId());
        OrderDto dto = OrderDto.builder()
                .id(updateOrder.getId())
                .userId(updateOrder.getUserId())
                .lessonId(updateOrder.getLessonId())
                .lessonName(updateOrder.getLessonName())
                .lessonPrice(updateOrder.getLessonPrice())
                .paymentType(updateOrder.getPaymentType())
                .build();
        Payment payment = paymentRepository.save(new Payment(dto));
        dto.setPaymentId(payment.getId());

        orderService.updateOrder(orderDto.getId(), OrderStatus.FINISHED);

        TicketSaveEvent ticketSaveEvent = TicketSaveEvent.builder()
                .id(updateOrder.getId())
                .paymentId(payment.getId())
                .userId(updateOrder.getUserId())
                .lessonId(updateOrder.getLessonId())
                .lessonName(updateOrder.getLessonName())
                .lessonPrice(updateOrder.getLessonPrice())
                .paymentType(updateOrder.getPaymentType())
                .build();
        log.info("ticket-save 이벤트 발신 : {} ", ticketSaveEvent);
        kafkaTicketTemplate.send("ticket-save-topic", ticketSaveEvent);

        PerformanceSaveEvent performanceSaveEvent = PerformanceSaveEvent.builder()
                .trainerId(orderDto.getTrainerId())
                .amount(updateOrder.getLessonPrice())
                .build();

        log.info("performance-save 이벤트 발신 : {} ", performanceSaveEvent);
        kafkaPerformanceTemplate.send("performance-save-topic", performanceSaveEvent);
        return payment;
    }

    public Order cancelPayment(OrderDto orderDto, String userId){
        paymentRepository.deleteById(orderDto.getPaymentId());
        return orderService.updateOrder(orderDto.getId(), OrderStatus.CANCEL);
    }

    public List<OrderDto> getPayments() {
        return orderRepository.findAll()
                .stream()
                .map(OrderDto::of)
                .collect(Collectors.toList());
    }
}
