package com.project.user.controller;

import com.google.gson.Gson;
import com.project.user.dto.LoginDto;
import com.project.user.dto.SignupDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void signupTest() throws Exception {
        String userId="test12";
        String password ="123456";
        String name = "테스트";

        SignupDto dto = SignupDto.builder()
                .userId(userId)
                .password(password)
                .name(name)
                .build();

        String json = new Gson().toJson(dto);

        mockMvc.perform(MockMvcRequestBuilders.post("/user-service/signup")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
