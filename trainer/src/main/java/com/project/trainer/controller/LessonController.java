package com.project.trainer.controller;

import com.project.trainer.Service.LessonService;
import com.project.trainer.domain.LessonType;
import com.project.trainer.domain.Lessons;
import com.project.trainer.domain.Record;
import com.project.trainer.dto.LessonDto;
import com.project.trainer.dto.RecordDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name="수업관리", description="수업관리 api")
@RestController
@RequiredArgsConstructor
public class LessonController {

    public final LessonService lessonService;

    @Operation(summary = "수업등록",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = Lessons.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Parameter", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @PostMapping("/trainer-service/lesson")
    public ResponseEntity<Lessons> saveLesson(@RequestBody LessonDto lessonDto,
                                             @RequestHeader(value = "user-id") String userId){
        Lessons lesson = lessonService.saveLesson(lessonDto, userId);
        return ResponseEntity.ok(lesson);
    }

    @Operation(summary = "수업상세조회",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = Lessons.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Parameter", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @GetMapping("/trainer-service/lesson/{lessonId}")
    public ResponseEntity getLesson(@PathVariable("lessonId") Long lessonId){
        LessonDto dto = lessonService.getLesson(lessonId);
        LessonResponse lessonResponse = new LessonResponse(dto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(lessonResponse);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class LessonResponse {
        private Long id;
        private String trainerId;
        private String lessonName;
        private Long price;
        private Long count;
        private LessonType lessonType;
        private LocalDate startDate;
        private LocalDate endDate;

        public LessonResponse(LessonDto lessonDto) {
            this.id = lessonDto.getId();
            this.trainerId = lessonDto.getTrainerId();
            this.lessonName = lessonDto.getLessonName();
            this.price = lessonDto.getPrice();
            this.count = lessonDto.getCount();
            this.lessonType = lessonDto.getLessonType();
            this.startDate = lessonDto.getStartDate();
            this.endDate = lessonDto.getEndDate();
        }
    }

    @Operation(summary = "수업목록조회",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = Lessons.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Parameter", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @GetMapping("/trainer-service/lesson")
    public ResponseEntity getLessons(){
        List<LessonDto> lessons = lessonService.findLessons();
        return ResponseEntity.status(HttpStatus.OK)
                .body(lessons);
    }

    @Operation(summary = "수업일지 등록",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = Record.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Parameter", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @PostMapping("/trainer-service/record")
    public ResponseEntity saveRecord(@RequestBody RecordDto recordDto,
                                     @RequestHeader(value = "user-id") String trainerId){
        Record record = lessonService.saveRecord(recordDto, trainerId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(record);
    }
}
