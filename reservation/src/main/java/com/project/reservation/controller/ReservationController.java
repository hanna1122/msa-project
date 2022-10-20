package com.project.reservation.controller;

import com.project.reservation.domain.Reservation;
import com.project.reservation.domain.ReservationStatus;
import com.project.reservation.dto.ReservationDto;
import com.project.reservation.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="예약관리", description="예약관리 api")
@RestController
@RequiredArgsConstructor
public class ReservationController {

    public final ReservationService reservationService;

    @Operation(summary = "예약등록",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = Reservation.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Parameter", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @PostMapping("/reservation-service/reservation")
    public ResponseEntity<Reservation> saveReservation(@RequestBody ReservationDto reservationDto,
                                                       @RequestHeader(value = "user-id") String userId){
        Reservation reservation = reservationService.saveReservation(reservationDto, userId);
        return ResponseEntity.ok(reservation);
    }


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    class ReservationStatusRequest{
        private ReservationStatus reservationStatus;
    }

    @Operation(summary = "예약조회",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = Reservation.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Parameter", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @GetMapping("/reservation-service/reservation/{trainerId}")
    public ResponseEntity<List> getReservations(@PathVariable("trainerId") Long trainerId,
                                                @RequestHeader(value = "user-id") String userId){
        List<ReservationDto> reservations = reservationService.findReservations(trainerId);
        return ResponseEntity.ok(reservations);
    }

    @Operation(summary = "예약상세조회",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = Reservation.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Parameter", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @GetMapping("/reservation-service/reservation")
    public ResponseEntity<Reservation> getReservation(@RequestBody ReservationDto reservationDto,
                                                @RequestHeader(value = "user-id") String userId){
        Reservation reservation = reservationService.getReservation(reservationDto.getId());
        return ResponseEntity.ok(reservation);
    }

    @Operation(summary = "예약확정",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = Reservation.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Parameter", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @PostMapping("/reservation-service/reservation/confirm/{reservationId}")
    public ResponseEntity<Reservation> confirmReservation(@PathVariable("reservationId") Long reservationId,
                                                    @RequestHeader(value = "user-id") String userId){
        Reservation reservationUpdate = reservationService.confirmReservation(reservationId, userId);
        return ResponseEntity.ok(reservationUpdate);
    }

    @Operation(summary = "예약취소",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = Reservation.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Parameter", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @PostMapping("/reservation-service/reservation/cancel/{reservationId}")
    public ResponseEntity<Reservation> cancelReservation(@PathVariable("reservationId") Long reservationId,
                                                          @RequestHeader(value = "user-id") String userId){
        Reservation reservationUpdate = reservationService.cancelReservation(reservationId, userId);
        return ResponseEntity.ok(reservationUpdate);
    }
}
