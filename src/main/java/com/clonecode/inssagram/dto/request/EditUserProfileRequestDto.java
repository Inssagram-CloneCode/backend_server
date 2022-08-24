package com.clonecode.inssagram.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class EditUserProfileRequestDto {
    @NotBlank(message = "유저 네임은 빈 칸일 수 없습니다.")
    private String username;
    private String intro;
}
