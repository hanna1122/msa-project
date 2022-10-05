package com.project.payment.feign.client;

import com.project.payment.dto.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("GYM-SERVICE")
public interface GymServiceClient {
    @PostMapping("/gym-service/ticket")
    void saveTicket(OrderDto orderDto, @RequestHeader(value="user-id") String userId);
}
