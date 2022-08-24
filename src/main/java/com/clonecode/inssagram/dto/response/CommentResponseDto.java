package com.clonecode.inssagram.dto.response;

import com.clonecode.inssagram.domain.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private UserProfileResponseDto user;
    private Long commentId;
    private String commentContents;

    public CommentResponseDto (Comment comment){
        this.user = new UserProfileResponseDto(comment.getUser());
        this.commentId = comment.getId();
        this.commentContents = comment.getCommentContents();
    }
}
