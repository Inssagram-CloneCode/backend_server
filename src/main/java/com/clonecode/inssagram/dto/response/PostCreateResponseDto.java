package com.clonecode.inssagram.dto.response;

import com.clonecode.inssagram.domain.Post;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;


@Getter
public class PostCreateResponseDto {
    @ApiModelProperty(example = "게시물 DB Id")
    @NotBlank
    private Long id;
    @ApiModelProperty(example = "게시물 사진 URL")
    @NotBlank
    private String imageUrl;
    @ApiModelProperty(example = "게시물 하트 수")
    @NotBlank
    private Long heartNum;
    @ApiModelProperty(example = "게시물 댓글 수")
    @NotBlank
    private Long commentNum;

    @Builder
    public PostCreateResponseDto(Post post, String imageUrl, Long heartNum, Long commentNum){
        this.id = post.getId();
        this.imageUrl = imageUrl;
        this.heartNum = heartNum;
        this.commentNum = commentNum;
    }
}
