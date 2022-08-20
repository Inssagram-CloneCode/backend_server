package com.clonecode.inssagram.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDto {
  @ApiModelProperty(example = "Bearer ")
  private String grantType;
  @ApiModelProperty(example = "엑세스 토큰")
  private String accessToken;
  @ApiModelProperty(example = "리프레시 토큰")
  private String refreshToken;
  @ApiModelProperty(example = "토큰 만료 시간")
  private Long accessTokenExpiresIn;
}
