package com.project.reservation.feign.client;

import com.project.reservation.feign.dto.TicketResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("GYM-SERVICE")
public interface GymServiceClient {

    @GetMapping("/gym-service/ticket/{ticketId}")
    TicketResponse getTicket(@PathVariable("ticketId") Long ticketId);

}
