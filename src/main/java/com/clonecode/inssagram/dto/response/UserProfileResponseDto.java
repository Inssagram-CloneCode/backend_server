package com.clonecode.inssagram.dto.response;

import com.clonecode.inssagram.domain.User;
import lombok.Getter;

@Getter
public class UserProfileResponseDto {
     private Long userId;
     private String username;
     private String profileImageUrl;


     public UserProfileResponseDto(User user){
          this.userId = user.getId();
          this.username= user.getUsername();
          this.profileImageUrl = user.getProfileImageUrl();
     }
}
