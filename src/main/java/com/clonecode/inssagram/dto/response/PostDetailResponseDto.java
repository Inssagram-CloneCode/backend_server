package com.clonecode.inssagram.dto.response;

import com.clonecode.inssagram.domain.Image;
import com.clonecode.inssagram.domain.Post;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostDetailResponseDto {
    @ApiModelProperty(example = "게시물 DB Id")
    private Long postId;
    @ApiModelProperty(example = "게시물 작성자 정보")
    private UserProfileResponseDto user;
    @ApiModelProperty(example = "게시물 글 내용")
    private String postContents;
    @ApiModelProperty(example = "게시물 사진 URL")
    private List<String> imageUrlList;
    @ApiModelProperty(example = "게시물 하트 수")
    private Long heartNum;
    @ApiModelProperty(example = "게시물 댓글 수")
    private Long commentNum;
    @ApiModelProperty(example = "로그인한 사용자의 게시물 하트 여부")
    private Long isHeart;
    @ApiModelProperty(example = "게시물 생성일자")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;
    @ApiModelProperty(example = "게시물 댓글 목록")
    private List<CommentResponseDto> commentList;

    @Builder
    public PostDetailResponseDto(Post post, Long heartNum, Long commentNum, Long isHeart){
        this.postId = post.getId();
        this.user = new UserProfileResponseDto(post.getUser());
        this.postContents = post.getPostContents();
        this.imageUrlList = post.getImageList().stream().map(Image::getImageUrl).collect(Collectors.toList());
        this.heartNum = heartNum;
        this.commentNum = commentNum;
        this.isHeart = isHeart;
        this.commentList = post.getCommentList().stream().map(CommentResponseDto::new).collect(Collectors.toList());
        this.createdAt = post.getCreatedAt();
    }
}