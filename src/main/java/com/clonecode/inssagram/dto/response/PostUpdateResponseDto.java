package com.clonecode.inssagram.dto.response;

import com.clonecode.inssagram.domain.Post;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class PostUpdateResponseDto {
    @ApiModelProperty(example = "게시물 글 내용")
    private String postContents;

    public PostUpdateResponseDto(Post post){
        this.postContents = post.getPostContents();
    }
}
