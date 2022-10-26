package com.project.gym.controller;


import com.google.gson.Gson;
import com.project.gym.domain.Ticket;
import com.project.gym.domain.UserType;
import com.project.gym.dto.TicketDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GymControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void saveTicketControllerTest() throws Exception {
        String userId = "user12";
        String useYn = "Y";
        Long orderId = 1L;
        UserType type = UserType.PERSONAL;
        Long count = 5L;


        TicketDto ticketDto = TicketDto.builder()
                .userId(userId)
                .useYn(useYn)
                .orderId(orderId)
                .type(type)
                .count(count)
                .build();


        String json = new Gson().toJson(ticketDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/gym-service/ticket")
                        .header("user-id", "user12")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    void getTicketsControllerTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/gym-service/tickets")
                        .header("user-id", "user12")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    void getTicketControllerTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/gym-service/ticket/1")
                        .header("user-id", "user12")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    void saveAttendanceControllerTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/gym-service/attendance")
                        .header("user-id", "user12")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }
}
