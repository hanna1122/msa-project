package com.project.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @NotBlank(message="아이디를 입력해주세요.")
    private String userId;

    @NotBlank(message="비밀번호를 입력해주세요.")
    private String password;

}
