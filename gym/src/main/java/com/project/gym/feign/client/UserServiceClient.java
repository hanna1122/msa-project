package com.project.gym.feign.client;

import com.project.gym.domain.UserType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("USER-SERVICE")
public interface UserServiceClient {
    @PutMapping("/user-service/{userId}")
    void updateUserType(@PathVariable("userId") String userId,@RequestParam("userType") UserType userType);
}
