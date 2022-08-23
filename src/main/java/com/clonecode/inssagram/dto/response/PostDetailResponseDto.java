package com.clonecode.inssagram.dto.response;

import com.clonecode.inssagram.domain.Comment;
import com.clonecode.inssagram.domain.Image;
import com.clonecode.inssagram.domain.Post;
import com.clonecode.inssagram.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostDetailResponseDto {
    private Long postId;
    private User user;
    private String postContents;
    private List<Image> imgUrlList;
    private int likeNum;
    private int commentNum;
    private int isLike;
    private LocalDateTime createdAt;

    private List<Comment> commentList;

    @Builder
    public PostDetailResponseDto(Post post, int likeNum, int commentNum, int isLike, List<Comment> commentList){
        this.postId = post.getId();
        this.user = post.getUser();
        this.postContents = post.getPostContents();
        this.imgUrlList = post.getImgUrlList();
        this.likeNum = likeNum;
        this.commentNum = commentNum;
        this.isLike = isLike;
        this.commentList = commentList;
        this.createdAt = post.getCreatedAt();
    }
}