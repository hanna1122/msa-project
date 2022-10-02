package com.project.trainer.service;

import com.project.trainer.domain.Lessons;
import com.project.trainer.domain.Trainers;
import com.project.trainer.dto.LessonDto;
import com.project.trainer.dto.TrainerDto;
import com.project.trainer.repository.LessonRepository;
import com.project.trainer.repository.TrainerRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class TrainerServiceTest {

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void signUpTest(){
        String userId = "trainer12";
        String password = "123456";
        String name = "test";

        TrainerDto dto = TrainerDto.builder()
                .userId(userId)
                .password(passwordEncoder.encode(password))
                .name(name)
                .build();


        Trainers trainer = trainerRepository.save(new Trainers(dto));
        assertEquals(trainer.getUserId(), userId);
        assertTrue(passwordEncoder.matches(password, trainer.getPassword()));
        assertEquals(trainer.getName(), name);
  }

}
