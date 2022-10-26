package com.project.trainer.service;

import com.project.trainer.domain.Lessons;
import com.project.trainer.domain.Record;
import com.project.trainer.dto.LessonDto;
import com.project.trainer.dto.RecordDto;
import com.project.trainer.repository.LessonRepository;
import com.project.trainer.repository.RecordRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class LessonServiceTest {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private RecordRepository recordRepository;

    @Test
    public void saveLessonTest(){
        String trainerId = "trainer12";
        String lessonName = "testLesson";
        Long price = 10000L;
        LocalDate startDate = LocalDate.parse("2022-09-29");
        LocalDate endDate = LocalDate.parse("2022-10-02");

        LessonDto dto = LessonDto.builder()
                .trainerId(trainerId)
                .lessonName(lessonName)
                .price(price)
                .startDate(startDate)
                .endDate(endDate)
                .build();


        Lessons lesson = lessonRepository.save(new Lessons(dto));
        assertEquals(lesson.getTrainerId(), trainerId);
        assertEquals(lesson.getLessonName(), lessonName);
        assertEquals(lesson.getPrice(), price);
        assertEquals(lesson.getStartDate(), startDate);
        assertEquals(lesson.getEndDate(), endDate);
    }

    @Test
    public void getLessonTest(){
        Long lessonId = 4L;
        String trainerId = "trainer12";
        String lessonName = "testLesson";
        Long price = 10000L;
        LocalDate startDate = LocalDate.parse("2022-09-29");
        LocalDate endDate = LocalDate.parse("2022-10-02");

        Lessons lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new RuntimeException("Lesson not found"));
        assertEquals(lesson.getTrainerId(), trainerId);
        assertEquals(lesson.getLessonName(), lessonName);
        assertEquals(lesson.getPrice(), price);
        assertEquals(lesson.getStartDate(), startDate);
        assertEquals(lesson.getEndDate(), endDate);
    }

    @Test
    public void saveRecordTest(){
        String userId = "user12";
        Long lessonId = 1L;
        String content = "Record TEST";
        Long time = 5L;
        String trainerId = "trainer12";

        RecordDto dto = RecordDto.builder()
                .userId(userId)
                .lessonId(lessonId)
                .content(content)
                .time(time)
                .build();

        Record savedRecord = recordRepository.save(new Record(dto, trainerId));
        assertEquals(savedRecord.getUserId(), userId);
        assertEquals(savedRecord.getLessonId(), lessonId);
        assertEquals(savedRecord.getContent(), content);
        assertEquals(savedRecord.getTime(), time);
    }
}
