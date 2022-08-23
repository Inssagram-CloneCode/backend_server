package com.clonecode.inssagram.dto.response;

import com.clonecode.inssagram.domain.Comment;
import com.clonecode.inssagram.domain.Image;
import com.clonecode.inssagram.domain.Post;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostDetailResponseDto {
    private Long postId;
    private UserProfileDto user;
    private String postContents;
    private List<String> imageUrlList;
    private int likeNum;
    private int commentNum;
    private int isHeart;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    private List<Comment> commentList;

    @Builder
    public PostDetailResponseDto(Post post, int likeNum, int commentNum, int isHeart, List<Comment> commentList){
        this.postId = post.getId();
        this.user = new UserProfileDto(post.getUser());
        this.postContents = post.getPostContents();
        this.imageUrlList = post.getImageList().stream().map(Image::getImageUrl).collect(Collectors.toList());
        this.likeNum = likeNum;
        this.commentNum = commentNum;
        this.isHeart = isHeart;
        this.commentList = commentList;
        this.createdAt = post.getCreatedAt();
    }
}