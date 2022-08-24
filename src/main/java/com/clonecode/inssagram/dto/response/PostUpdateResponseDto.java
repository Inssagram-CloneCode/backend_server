package com.clonecode.inssagram.dto.response;

import com.clonecode.inssagram.domain.Post;
import lombok.Getter;

@Getter
public class PostUpdateResponseDto {
    private String postContents;

    public PostUpdateResponseDto(Post post){
        this.postContents = post.getPostContents();
    }
}
