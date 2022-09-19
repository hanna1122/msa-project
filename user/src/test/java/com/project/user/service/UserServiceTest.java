package com.project.user.service;

import com.project.user.Repository.UserRepository;
import com.project.user.domain.Users;
import com.project.user.dto.LoginDto;
import com.project.user.dto.SignupDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void signupTest(){
        String userId = "test12";
        String password = "123456";
        String name = "테스트";

        SignupDto dto = SignupDto.builder()
                .userId(userId)
                .password(passwordEncoder.encode(password))
                .name(name)
                .build();

        Users users = userRepository.save(new Users(dto));
        assertEquals(users.getUserId(), userId);
        assertTrue(passwordEncoder.matches(password,users.getPassword()));
        assertEquals(users.getName(), name);
    }
}
