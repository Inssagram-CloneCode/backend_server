package com.clonecode.inssagram.dto.response;

import com.clonecode.inssagram.domain.Image;
import com.clonecode.inssagram.domain.Post;
import com.clonecode.inssagram.domain.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostAllResponseDto {
    private UserProfileDto user;
    private String postContents;
    private List<String> imageUrlList;
    private int likeNum;
    private int commentNum;
    private int isHeart;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    @Builder
    public PostAllResponseDto(User user, Post post, int likeNum, int commentNum, int isHeart){
        this.user = new UserProfileDto(user);
        this.postContents = post.getPostContents();
        this.imageUrlList = post.getImageList().stream().map(Image::getImageUrl).collect(Collectors.toList());
        this.createdAt = post.getCreatedAt();
        this.likeNum = likeNum;
        this.commentNum = commentNum;
        this.isHeart = isHeart;
    }

    @Builder
    public PostAllResponseDto(Post post){
        this.postContents = post.getPostContents();
    }
}
