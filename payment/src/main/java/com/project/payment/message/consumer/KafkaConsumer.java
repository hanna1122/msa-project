package com.project.payment.message.consumer;

import com.project.payment.message.event.PaymentRollbackEvent;
import com.project.payment.repository.OrderRepository;
import com.project.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaConsumer {
    public final OrderRepository orderRepository;
    public final PaymentRepository paymentRepository;

    @KafkaListener(topics = "payment-rollback-topic", groupId = "myGroup")
    public void consume(PaymentRollbackEvent event) throws IOException {
        log.info("paymentRollback 이벤트 수신");
        log.info("[orderId : {}, paymentId : {}] is deleted.", event.getOrderId(), event.getPaymentId());

        orderRepository.deleteById(event.getOrderId());
        paymentRepository.deleteById(event.getPaymentId());
    }
}