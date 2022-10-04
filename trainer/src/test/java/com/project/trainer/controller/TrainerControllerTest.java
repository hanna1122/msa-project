package com.project.trainer.controller;


import com.google.gson.Gson;
import com.project.trainer.domain.LessonType;
import com.project.trainer.dto.LessonDto;
import com.project.trainer.dto.TrainerDto;
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
public class TrainerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void signUpTest() throws Exception{
        String trainerId = "trainer12";
        String password = "123456";
        String name = "test";

        TrainerDto dto = TrainerDto.builder()
                .userId(trainerId)
                .password(password)
                .name(name)
                .build();

        String json = new Gson().toJson(dto);

        mockMvc.perform(MockMvcRequestBuilders.post("/trainer-service/user/signup")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
