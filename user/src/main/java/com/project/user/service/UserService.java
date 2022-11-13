package com.project.user.service;

import com.project.user.dto.Result;
import com.project.user.exception.CustomException;
import com.project.user.message.event.UserTypeUpdatedEvent;
import com.project.user.repository.UserRepository;
import com.project.user.domain.Users;
import com.project.user.dto.SignupDto;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public Users signUp(SignupDto signupDto){

        this.validateDuplicateUserId(signupDto.getUserId());

        SignupDto dto = SignupDto.builder()
                 .userId(signupDto.getUserId())
                 .password(passwordEncoder.encode(signupDto.getPassword()))
                 .name(signupDto.getName())
                 .build();

        return userRepository.save(new Users(dto));
    }

    @Override
    @RateLimiter(name = "userService", fallbackMethod = "loginFallback")
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Users user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new User(user.getUserId(), user.getPassword(),
                true, true, true, true,
                Collections.emptyList()
        );
    }

    @RateLimiter(name = "userService", fallbackMethod = "getUserFallback")
    public Result getUser(String userId){
        Users user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(HttpStatus.CONFLICT, "User not found"));
        return Result.createSuccessResult(user);
    }

    public void validateDuplicateUserId(String userId) {
        if (userRepository.existsByUserId(userId)) {
            throw new DuplicateKeyException("userId");
        }
    }

    public void updateUserType(UserTypeUpdatedEvent userTypeUpdatedEvent){
        Users user = (Users) getUser(userTypeUpdatedEvent.getUserId()).getData();
        user.setUserType(userTypeUpdatedEvent.getUserType());
        Users updatedUser = userRepository.save(user);
    }

    private UserDetails loginFallback(Throwable t) {
        System.out.println("fallback invoked! exception type : " + t.getClass());
        return null;
    }
    private Result getUserFallback(Throwable t) {
        String message = "fallback invoked! exception type : " + t.getClass();
        return Result.createErrorResult(message);
    }

    @CircuitBreaker(name = "user", fallbackMethod = "testFallback")
    public String CircuitBreakerTest() {
        randomException();
        return "CircuitBreakerTest";
    }

    private void randomException() {
        int randomInt = new Random().nextInt(10);

        if(randomInt <= 7) {
            throw new RuntimeException("failed");
        }
    }

    @Bulkhead(name = "userService", type = Bulkhead.Type.SEMAPHORE, fallbackMethod = "testFallback")
    public String bulkheadTest() {
        return "bulkheadTest";
    }

    private String testFallback(Throwable t) {
        return "fallback invoked! exception type : " + t.getClass();
    }





}
