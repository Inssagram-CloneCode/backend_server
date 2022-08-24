package com.clonecode.inssagram.dto.response;

import com.clonecode.inssagram.domain.Post;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;


@Getter
public class PostCreateResponseDto {
    @ApiModelProperty(example = "게시물 Id")
    @NotBlank
    private Long postId;
    @ApiModelProperty(example = "사진 url")
    @NotBlank
    private String imageUrl;
    @ApiModelProperty(example = "게시물 좋아요 수")
    @NotBlank
    private Long heartNum;
    @ApiModelProperty(example = "게시물 댓글 수")
    @NotBlank
    private Long commentNum;

    @Builder
    public PostCreateResponseDto(Post post, String imageUrl, Long heartNum, Long commentNum){
        this.postId = post.getId();
        this.imageUrl = imageUrl;
        this.heartNum = heartNum;
        this.commentNum = commentNum;
    }
}
