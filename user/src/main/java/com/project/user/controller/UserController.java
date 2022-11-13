package com.project.user.controller;

import com.project.user.domain.Users;
import com.project.user.dto.Result;
import com.project.user.dto.SignupDto;
import com.project.user.service.UserService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="회원관리", description="회원관리 api")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "회원가입",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = Users.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Parameter", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @PostMapping("/user-service/user/signup")
    public ResponseEntity<Users> signup(@RequestBody SignupDto signupDto){
        Users user = userService.signUp(signupDto);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "유저 정보",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = Users.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Parameter", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @GetMapping("/user-service/users/getUser")
    public ResponseEntity<Result> getUser(@RequestHeader(value = "user-id") String userId){
        Result result = userService.getUser(userId);
        return ResponseEntity.ok(result);
    }



}
