package com.project.reservation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.project.reservation.config.TestConfig;
import com.project.reservation.dto.ReservationDto;
import com.project.reservation.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDate;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ReservationService reservationService;

    @Test
    void saveReservationControllerTest() throws Exception {
        String userId = "user12";
        String userName = "test";
        Long ticketId = 1L;
        Long lessonId = 1L;
        Long trainerId = 1L;
        LocalDate date = LocalDate.now();

        ReservationDto reservationDto = ReservationDto.builder()
                .reservatorId(userId)
                .reservatorName(userName)
                .ticketId(ticketId)
                .trainerId(trainerId)
                .lessonId(lessonId)
                .reservationDate(date)
                .build();

        String json = new Gson().toJson(reservationDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/reservation-service/reservation")
                        .header("user-id", "user12")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());


    }

    @Test
    void getReservationControllerTest() throws Exception {
        Long reservationId = 1L;

        ReservationDto reservationDto = ReservationDto.builder()
                .id(reservationId)
                .build();

        String json = new Gson().toJson(reservationDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/reservation-service/reservation")
                        .header("user-id", "user12")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    void findReservationsControllerTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/reservation-service/reservation/1")
                        .header("user-id", "trainer12")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());


    }


}
