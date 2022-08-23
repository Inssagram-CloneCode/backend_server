package com.clonecode.inssagram.dto.response;

import com.clonecode.inssagram.domain.Image;
import com.clonecode.inssagram.domain.Post;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;


@Getter
public class PostCreateResponseDto {
    @ApiModelProperty(example = "게시물 Id")
    @NotBlank
    private Long id;
    @ApiModelProperty(example = "사진 url")
    @NotBlank
    private List<String> imageUrlList;
    @ApiModelProperty(example = "게시물 좋아요 수")
    @NotBlank
    private int likeNum;
    @ApiModelProperty(example = "게시물 댓글 수")
    @NotBlank
    private int commentNum;

    @Builder
    public PostCreateResponseDto(Post post, int likeNum, int commentNum){
        this.id = post.getId();
        this.imageUrlList = post.getImageList().stream().map(Image::getImageUrl).collect(Collectors.toList());
        this.likeNum = likeNum;
        this.commentNum = commentNum;
    }
}
