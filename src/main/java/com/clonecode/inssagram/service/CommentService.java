package com.clonecode.inssagram.service;

import com.clonecode.inssagram.domain.Comment;
import com.clonecode.inssagram.domain.Post;
import com.clonecode.inssagram.domain.User;
import com.clonecode.inssagram.domain.UserDetailsImpl;
import com.clonecode.inssagram.dto.request.CommentRequestDto;
import com.clonecode.inssagram.dto.response.ResponseDto;
import com.clonecode.inssagram.exception.EntityNotFoundException;
import com.clonecode.inssagram.exception.InvalidValueException;
import com.clonecode.inssagram.global.error.ErrorCode;
import com.clonecode.inssagram.jwt.TokenProvider;
import com.clonecode.inssagram.repository.CommentRepository;
import com.clonecode.inssagram.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    private final TokenProvider tokenProvider;

    // 댓글 작성
    @Transactional
    public ResponseDto<?> createComment(User user, Long postId, CommentRequestDto requestDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.POST_NOT_FOUND));
        Comment comment = new Comment(user, post, requestDto);

        commentRepository.save(comment);

        return ResponseDto.success("댓글 작성이 완료되었습니다.");
    }

    // 댓글 삭제
    @Transactional
    public ResponseDto<?> deleteComment(Long commentId, UserDetailsImpl userDetails) {
        commentRepository.findById(commentId).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.COMMENT_NOT_FOUND));
        Long writerId = commentRepository.findById(commentId).get().getUser().getId();
        Long userId = tokenProvider.getUserFromAuthentication().getId();
        if (Objects.equals(writerId, userId)) {
            commentRepository.deleteById(commentId);
        } else {
            throw new InvalidValueException(ErrorCode.COMMENT_UNAUTHORIZED);
        }

        return ResponseDto.success("댓글 삭제가 완료되었습니다.");
    }


    // 댓글 조회(Post 조회 시 댓글 전체 리스트 Response)
}
