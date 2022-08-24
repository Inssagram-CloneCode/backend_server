package com.clonecode.inssagram.dto.response;

import com.clonecode.inssagram.domain.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class UserProfileResponseDto {
     @ApiModelProperty(example = "사용자 DB Id")
     private Long userId;
     @ApiModelProperty(example = "사용자 이름(별명)")
     private String username;
     @ApiModelProperty(example = "사용자 프로필 사진 URL")
     private String profileImageUrl;


     public UserProfileResponseDto(User user){
          this.userId = user.getId();
          this.username= user.getUsername();
          this.profileImageUrl = user.getProfileImageUrl();
     }
}
