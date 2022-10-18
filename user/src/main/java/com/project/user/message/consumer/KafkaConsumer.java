package com.project.user.message.consumer;

import com.project.user.domain.Users;
import com.project.user.message.event.UserTypeUpdatedEvent;
import com.project.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaConsumer {
    private final UserService userService;

    @KafkaListener(topics = "userType-updated-topic", groupId = "myGroup")
    public void consume(UserTypeUpdatedEvent event) throws IOException {
        log.info("userTypeUpdated 이벤트 수신");
        log.info("[userId : {}, data : {}] is updated.", event.getUserId(), event);
        userService.getUser(event.getUserId());
        userService.updateUserType(event);
    }
}