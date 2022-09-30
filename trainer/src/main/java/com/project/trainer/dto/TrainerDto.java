package com.project.trainer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainerDto {
    @NotBlank(message="아이디를 입력해주세요.")
    private String userId;

    @NotBlank(message="비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message="이름을 입력해주세요.")
    private String name;
}
