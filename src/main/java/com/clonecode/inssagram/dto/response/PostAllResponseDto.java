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
    private UserDto user;
    private String postContents;
    private List<String> imgUrlList;
    private int likeNum;
    private int commentNum;
    private int isLike;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    @Builder
    public PostAllResponseDto(User user,Post post, int likeNum, int commentNum, int isLike){
        this.user =  new UserDto(post.getUser());
        this.postContents = post.getPostContents();
        this.imgUrlList = post.getImgList().stream().map(Image::getImageUrl).collect(Collectors.toList());
        this.createdAt = post.getCreatedAt();
        this.likeNum = likeNum;
        this.commentNum = commentNum;
        this.isLike = isLike;
    }

    @Builder
    public PostAllResponseDto(Post post){
        this.postContents = post.getPostContents();
    }
}
