package com.clonecode.inssagram.controller;

import com.clonecode.inssagram.domain.User;
import com.clonecode.inssagram.domain.UserDetailsImpl;
import com.clonecode.inssagram.dto.request.PostRequestDto;
import com.clonecode.inssagram.dto.response.AllPostResponseDto;
import com.clonecode.inssagram.dto.response.PostResponseDto;
import com.clonecode.inssagram.dto.response.ResponseDto;
import com.clonecode.inssagram.jwt.TokenProvider;
import com.clonecode.inssagram.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final TokenProvider tokenProvider;

    @ApiOperation(value = "게시물 작성", notes = "게시물 작성 기능", response = PostResponseDto.class)
    @PostMapping
    public ResponseDto<PostResponseDto> createPost (
            @RequestBody PostRequestDto requestDto){
        User user = tokenProvider.getUserFromAuthentication();
        return ResponseDto.success(postService.writePost(user, requestDto));
    }

}
