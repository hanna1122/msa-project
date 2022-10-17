package com.project.gym.message.producer;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/gym-service/kafka")
public class KafkaController {
    private final KafkaProducer producer;


    @PostMapping
    public String sendMessage(@RequestParam(value = "message", required = false) String message) {
        System.out.println(" message  "+message);
        this.producer.sendMessage(message);

        return "success";
    }
}