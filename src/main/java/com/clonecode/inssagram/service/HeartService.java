package com.clonecode.inssagram.service;

import com.clonecode.inssagram.domain.Heart;
import com.clonecode.inssagram.domain.Post;
import com.clonecode.inssagram.domain.User;
import com.clonecode.inssagram.dto.request.HeartRequestDto;
import com.clonecode.inssagram.dto.response.HeartResponseDto;
import com.clonecode.inssagram.dto.response.ResponseDto;
import com.clonecode.inssagram.exception.EntityNotFoundException;
import com.clonecode.inssagram.exception.InvalidValueException;
import com.clonecode.inssagram.global.error.ErrorCode;
import com.clonecode.inssagram.jwt.TokenProvider;
import com.clonecode.inssagram.repository.HeartRepository;
import com.clonecode.inssagram.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HeartService {
    private final HeartRepository heartRepository;
    private final PostRepository postRepository;
    private final TokenProvider tokenProvider;


    @Transactional
    public ResponseDto<?> toggleHeart(Long postId, HeartRequestDto requestDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () ->  new EntityNotFoundException(ErrorCode.POST_NOT_FOUND));

        User user = tokenProvider.getUserFromAuthentication();
        if (user == null) {
            throw new InvalidValueException(ErrorCode.INVALID_TOKEN);
        }
        Optional<Heart> chkPostHeart = heartRepository.findByPostAndUser(post, user);

        if (requestDto.getIsHeart() == 0) {
            if (chkPostHeart.isEmpty()){
                approveHeartRequest(new Heart(user, post, 1L));
            } else {
                chkPostHeart.get().setIsHeart(1L);
            }

            post.count(chkHeart(post));

            return ResponseDto.success(
                    HeartResponseDto.builder()
                            .isHeart(1L)
                            .heartNum(chkHeart(post))
                            .build()
            );
        }

        chkPostHeart.ifPresent(heart -> heart.setIsHeart(0L));
        post.count(chkHeart(post));
        return ResponseDto.success(
                HeartResponseDto.builder()
                        .isHeart(0L)
                        .heartNum(chkHeart(post))
                        .build()
        );

    }

    private void approveHeartRequest(Heart heart) {
        heartRepository.save(heart);
    }

    public Long chkHeart(Post post) {
        return heartRepository.countAllByPostAndIsHeart(post, 1L);
    }

}
