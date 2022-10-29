package com.project.trainer.controller;

import com.project.trainer.Service.TrainerService;
import com.project.trainer.domain.Performance;
import com.project.trainer.domain.Trainers;
import com.project.trainer.dto.PerformanceDto;
import com.project.trainer.dto.TrainerDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="강사관리", description="강사관리 api")
@RestController
@RequiredArgsConstructor
public class TrainerController {

    public final TrainerService trainerService;

    @Operation(summary = "강사 회원가입",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = Trainers.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Parameter", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @PostMapping("/trainer-service/user/signup")
    public ResponseEntity<Trainers> signup(@RequestBody TrainerDto trainerDto){
        Trainers user = trainerService.signUp(trainerDto);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "강사 실적추가",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = Trainers.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Parameter", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @PatchMapping("/trainer-service/performance")
    public ResponseEntity<Performance> addPerformance(@RequestBody PerformanceDto performanceDto, @RequestHeader(value = "user-id") String userId){
        Performance addPerformance = trainerService.addPerformance(performanceDto);
        return ResponseEntity.ok(addPerformance);
    }

    @Operation(summary = "강사 실적조회",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = Trainers.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Parameter", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @GetMapping("/trainer-service/performances/{trainerId}")
    public ResponseEntity<List> getPerformances(@PathVariable Long trainerId, @RequestHeader(value = "user-id") String userId){
        List<PerformanceDto> performances = trainerService.getPerformances(trainerId);
        return ResponseEntity.ok(performances);
    }

    @Operation(summary = "강사 삭제",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "400", description = "Bad Parameter", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @DeleteMapping("/trainer-service/trainer/{trainerId}")
    public ResponseEntity deleteTrainer(@PathVariable("trainerId") Long trainerId,
                                        @RequestHeader(value = "user-id") String userId){
        trainerService.deleteTrainer(trainerId);
        return ResponseEntity.ok("success");
    }
}
