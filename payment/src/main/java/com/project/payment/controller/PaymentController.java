package com.project.payment.controller;

import com.project.payment.domain.Order;
import com.project.payment.domain.Payment;
import com.project.payment.dto.OrderDto;
import com.project.payment.service.PaymentService;
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

@Tag(name="결제관리", description="결제관리 api")
@RestController
@RequiredArgsConstructor
public class PaymentController {

    public final PaymentService paymentService;

    @Operation(summary = "결제확인",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = Payment.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Parameter", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @PostMapping("/payment-service/payment")
    public ResponseEntity<Payment> savePayment(@RequestBody OrderDto orderDto,
                                           @RequestHeader(value = "user-id") String userId){
        Payment payment = paymentService.savePayment(orderDto, userId);
        return ResponseEntity.ok(payment);
    }

    @Operation(summary = "결제취소",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = Order.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Parameter", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @PostMapping("/payment-service/cancel")
    public ResponseEntity<Order> cancelPayment(@RequestBody OrderDto orderDto,
                                               @RequestHeader(value = "user-id") String userId){
        Order orderUpdate = paymentService.cancelPayment(orderDto, userId);
        return ResponseEntity.ok(orderUpdate);
    }
}
