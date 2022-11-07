package com.project.reservation.feign.client;

import com.project.reservation.dto.Result;
import com.project.reservation.feign.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("USER-SERVICE")
public interface UserServiceClient {

    @GetMapping("/user-service/users/getUser")
    Result<UserResponse> getUser(@RequestHeader(value = "user-id") String userId);

}
