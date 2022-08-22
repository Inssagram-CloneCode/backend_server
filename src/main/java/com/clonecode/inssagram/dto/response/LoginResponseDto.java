package com.clonecode.inssagram.dto.response;

import com.clonecode.inssagram.dto.TokenDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    @ApiModelProperty(example = "사용자 DB Id")
    private Long userId;
    @ApiModelProperty(example = "사용자 이메일")
    private String email;
    @ApiModelProperty(example = "사용자 이름(별명)")
    private String username;
    @ApiModelProperty(example = "사용자 프로필 사진 URL")
    private String profileImgUrl;
    @ApiModelProperty(example = "인증 토큰")
    private TokenDto token;
}
