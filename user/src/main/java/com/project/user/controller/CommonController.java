package com.project.user.controller;

import com.project.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="test", description="test api")
@RestController
@RequiredArgsConstructor
public class CommonController {
    private final Environment env;

    private final UserService userService;

    @Operation(summary = "테스트",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "400", description = "Bad Parameter", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @GetMapping("/user-service/user/healthCheck")
    public String healthCheck(){

        return String.format("It's working in User Service on PORT %s",
                env.getProperty("local.server.port"));
    }


    @GetMapping("/user-service/CircuitBreakerTest")
    public ResponseEntity<String> CircuitBreakerTest() {
        String test = userService.CircuitBreakerTest();
        return ResponseEntity.ok(test);
    }

    @GetMapping("/user-service/bulkheadTest")
    public ResponseEntity<String> bulkheadTest() {
        return ResponseEntity.ok(userService.bulkheadTest());
    }
}
