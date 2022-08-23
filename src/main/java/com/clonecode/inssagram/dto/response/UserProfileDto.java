package com.clonecode.inssagram.dto.response;

import com.clonecode.inssagram.domain.User;
import lombok.Getter;

@Getter
public class UserProfileDto {
     private Long userId;
     private String username;
     private String profileImageUrl;


     public UserProfileDto(User user){
          this.userId = user.getUserId();
          this.username= user.getUsername();
          this.profileImageUrl = user.getProfileImageUrl();
     }
}
