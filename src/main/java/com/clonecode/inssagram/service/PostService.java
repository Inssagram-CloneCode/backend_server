package com.clonecode.inssagram.service;

import com.clonecode.inssagram.domain.Post;
import com.clonecode.inssagram.domain.User;
import com.clonecode.inssagram.dto.request.PostRequestDto;
import com.clonecode.inssagram.dto.response.PostResponseDto;
import com.clonecode.inssagram.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public PostResponseDto writePost(User user, PostRequestDto requestDto) {
        Post post = new Post (user, requestDto);

        postRepository.save(post);
        return PostResponseDto.builder()
                .post(post)
                .likeNum(0)
                .commentNum(0)
                .build();
    }

}
