package com.project.payment.controller;

import com.project.payment.domain.Order;
import com.project.payment.domain.OrderStatus;
import com.project.payment.dto.OrderDto;
import com.project.payment.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name="주문관리", description="주문관리 api")
@RestController
@RequiredArgsConstructor
public class OrderController {

    public final OrderService orderService;

    @Operation(summary = "주문등록",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = Order.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Parameter", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @PostMapping("/payment-service/order")
    public ResponseEntity<Order> saveOrder(@RequestBody OrderDto orderDto,
                                           @RequestHeader(value = "user-id") String userId){
        Order order = orderService.saveOrder(orderDto, userId);
        return ResponseEntity.ok(order);
    }

    @Operation(summary = "주문 상세조회",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = Order.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Parameter", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @GetMapping("/payment-service/order/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable Long orderId,
                                          @RequestHeader(value = "user-id") String userId){
        Order order = orderService.getOrder(orderId);
        return ResponseEntity.ok(order);
    }

    @Operation(summary = "주문 리스트조회",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = Order.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Parameter", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @GetMapping("/payment-service/orders")
    public ResponseEntity<List> getOrders(@RequestHeader(value = "user-id") String userId){
        List<OrderDto> order = orderService.getOrders(userId);
        return ResponseEntity.ok(order);
    }

    @Operation(summary = "주문상태수정",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = Order.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Parameter", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @PatchMapping("/payment-service/order/{orderId}")
    public ResponseEntity<Order> cancelOrder(@PathVariable("orderId") Long orderId){
        Order orderUpdate = orderService.updateOrder(orderId, OrderStatus.CANCEL);
        return ResponseEntity.ok(orderUpdate);
    }


}
