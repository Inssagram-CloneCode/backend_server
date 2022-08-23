package com.clonecode.inssagram.dto.response;

import com.clonecode.inssagram.domain.Image;
import com.clonecode.inssagram.domain.Post;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostAllResponseDto {
    private UserProfileResponseDto user;
    private String postContents;
    private List<String> imageUrlList;
    private Long heartNum;
    private Long commentNum;
    private Long isHeart;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    @Builder
    public PostAllResponseDto(Post post, Long heartNum, Long commentNum, Long isHeart){
        this.user = new UserProfileResponseDto(post.getUser());
        this.postContents = post.getPostContents();
        this.imageUrlList = post.getImageList().stream().map(Image::getImageUrl).collect(Collectors.toList());
        this.createdAt = post.getCreatedAt();
        this.heartNum = heartNum;
        this.commentNum = commentNum;
        this.isHeart = isHeart;
    }

    @Builder
    public PostAllResponseDto(Post post){
        this.postContents = post.getPostContents();
    }
}
