package com.clonecode.inssagram.dto.response;

import com.clonecode.inssagram.dto.TokenDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    private Long userId;
    private String email;
    private String username;
    private String profileImgUrl;
    private TokenDto token;
}
