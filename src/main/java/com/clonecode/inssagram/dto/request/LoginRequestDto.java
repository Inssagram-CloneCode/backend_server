package com.clonecode.inssagram.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

    @NotBlank
    @ApiModelProperty(example = "사용자 이메일")
    private String email;

    @NotBlank
    @ApiModelProperty(example = "사용자 비밀번호")
    private String password;

}
