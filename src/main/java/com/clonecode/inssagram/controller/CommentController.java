package com.clonecode.inssagram.controller;

import com.clonecode.inssagram.domain.User;
import com.clonecode.inssagram.domain.UserDetailsImpl;
import com.clonecode.inssagram.dto.request.CommentRequestDto;
import com.clonecode.inssagram.dto.response.ResponseDto;
import com.clonecode.inssagram.jwt.TokenProvider;
import com.clonecode.inssagram.service.CommentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final TokenProvider tokenProvider;

    @ApiOperation(value = "댓글 작성", notes = "댓글 작성 기능", response = ResponseDto.class)
    @PostMapping("/{postId}")
    public ResponseEntity creatComment(@PathVariable(name = "postId") Long postId,
                                       @RequestBody CommentRequestDto requestDto) {
        User user = tokenProvider.getUserFromAuthentication();

        return new ResponseEntity<>(
                commentService.createComment(user, postId, requestDto),
                HttpStatus.valueOf(HttpStatus.OK.value()));
    }

    @ApiOperation(value = "댓글 삭제", notes = "댓글 삭제 기능", response = ResponseDto.class)
    @DeleteMapping("/{commentId}")
    public ResponseEntity deleteComment(@PathVariable(name = "commentId") Long commentId,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return new ResponseEntity(
                commentService.deleteComment(commentId, userDetails),
                HttpStatus.valueOf(HttpStatus.OK.value()));
    }
}
