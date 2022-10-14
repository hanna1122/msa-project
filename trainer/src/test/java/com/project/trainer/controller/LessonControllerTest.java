package com.project.trainer.controller;

import com.google.gson.Gson;
import com.project.trainer.domain.LessonType;
import com.project.trainer.dto.LessonDto;
import com.project.trainer.dto.RecordDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LessonControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void saveLessonTest() throws Exception{
        String trainerId = "hong12";
        String lessonName = "lessontest12";
        Long price = 10003L;
        Long count = 5L;

        LessonDto dto = LessonDto.builder()
                .trainerId(trainerId)
                .lessonName(lessonName)
                .price(price)
                .count(count)
                .lessonType(LessonType.PERSONAL)
                .build();

        String json = new Gson().toJson(dto);

        mockMvc.perform(MockMvcRequestBuilders.post("/trainer-service/lesson")
                        .header("user-id", "user12")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void saveRecordTest() throws Exception{
        String userId = "user12";
        Long lessonId = 1L;
        String content = "Record TEST";
        Long time = 5L;

        RecordDto dto = RecordDto.builder()
                .userId(userId)
                .lessonId(lessonId)
                .content(content)
                .time(time)
                .build();

        String json = new Gson().toJson(dto);

        mockMvc.perform(MockMvcRequestBuilders.post("/trainer-service/record")
                        .header("user-id", "trainer12")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
