package com.clonecode.inssagram.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {

    //이메일 주소의 특수문자 중 점( . ) 하이픈( - ) 언더바( _ ) 만 사용 가능
    @NotBlank
    @Size(min = 4, max = 32)
    @Pattern(regexp = "^([\\w\\.\\_\\-])*[a-zA-Z0-9]+([\\w\\.\\_\\-])*([a-zA-Z0-9])+([\\w\\.\\_\\-])+@([a-zA-Z0-9]+\\.)+[a-zA-Z0-9]{2,8}$")
    private String email;

    //영어 대소문자, 숫자 3~12자리
    @NotBlank
    @Size(min = 4, max = 12)
    @Pattern(regexp = "[a-zA-Z\\d]*${3,12}")
    private String username;

    //영어 소문자, 숫자 4~32자리
    @NotBlank
    @Size(min = 4, max = 32)
    @Pattern(regexp = "[a-z\\d]*${3,32}")
    private String password;

    @NotBlank
    private String passwordCheck;
}
