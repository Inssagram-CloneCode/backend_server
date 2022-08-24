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
    @ApiModelProperty(example = "tester@inssa.com")
    private String email;

    @NotBlank
    @ApiModelProperty(example = "1234")
    private String password;

}
