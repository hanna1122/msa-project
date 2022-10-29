package com.project.trainer.controller;


import com.google.gson.Gson;
import com.project.trainer.dto.PerformanceDto;
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


    @Test
    public void addPerformanceTest() throws Exception{
        Long trainerId = 1L;
        Long amount = 10000L;
        Long lessonCount = 5L;


        PerformanceDto dto = PerformanceDto.builder()
                .trainerId(trainerId)
                .amount(amount)
                .lessonCount(lessonCount)
                .build();

        String json = new Gson().toJson(dto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/trainer-service/performance")
                        .header("user-id", "trainer12")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getPerformancesTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/trainer-service/performances/1")
                        .header("user-id", "trainer12")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteTrainerTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/trainer-service/trainer/1")
                        .header("user-id", "trainer12")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
