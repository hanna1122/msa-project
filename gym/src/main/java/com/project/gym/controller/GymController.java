package com.project.gym.controller;

import com.project.gym.domain.Ticket;
import com.project.gym.dto.TicketDto;
import com.project.gym.feign.dto.OrderRequest;
import com.project.gym.service.GymService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="헬스장관리", description="헬스장관리 api")
@RestController
@RequiredArgsConstructor
public class GymController {
    private final GymService gymService;

    @Operation(summary = "이용권등록",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = Ticket.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Parameter", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @PostMapping("/gym-service/ticket")
    public ResponseEntity<Ticket> saveTicket(@RequestBody OrderRequest orderRequest,
                                           @RequestHeader(value = "user-id") String userId){
        Ticket ticket = gymService.saveTicket(orderRequest, userId);
        return ResponseEntity.ok(ticket);
    }

}
