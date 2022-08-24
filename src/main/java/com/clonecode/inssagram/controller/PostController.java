package com.clonecode.inssagram.controller;

import com.clonecode.inssagram.domain.User;
import com.clonecode.inssagram.dto.request.PostRequestDto;
import com.clonecode.inssagram.dto.response.PostAllResponseDto;
import com.clonecode.inssagram.dto.response.PostCreateResponseDto;
import com.clonecode.inssagram.dto.response.PostDetailResponseDto;
import com.clonecode.inssagram.dto.response.PostUpdateResponseDto;
import com.clonecode.inssagram.exception.InvalidValueException;
import com.clonecode.inssagram.global.error.ErrorCode;
import com.clonecode.inssagram.jwt.TokenProvider;
import com.clonecode.inssagram.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(tags = {"게시물 API Controller"})
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final TokenProvider tokenProvider;

    @ApiOperation(value = "게시물 작성", notes = "게시물 작성 기능", response = PostCreateResponseDto.class)
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createPost(
            @RequestPart PostRequestDto requestDto,
            @RequestPart List<MultipartFile> imageFileList) {

        User user = tokenProvider.getUserFromAuthentication();
        if (imageFileList == null) {
            throw new InvalidValueException(ErrorCode.NO_FILE_TO_UPLOAD);
        }
        return new ResponseEntity<>(postService.writePost(user, requestDto, imageFileList), HttpStatus.valueOf(HttpStatus.CREATED.value()));
    }

    @ApiOperation(value = "메인페이지 게시물 조회", notes = "전체 게시물 조회 기능", response = PostAllResponseDto.class)
    @GetMapping
    public ResponseEntity<?> getPosts() {
        if (tokenProvider.getUserFromAuthentication() == null) {
            throw new InvalidValueException(ErrorCode.LOGIN_REQUIRED);
        }
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.valueOf(HttpStatus.FOUND.value()));
    }

    @ApiOperation(value = "상세 게시물 조회", notes = "상세 게시물 조회 기능", response = PostDetailResponseDto.class)
    @GetMapping("/{postId}")
    public ResponseEntity<?> getPost(@PathVariable Long postId) {
        if (tokenProvider.getUserFromAuthentication() == null) {
            throw new InvalidValueException(ErrorCode.LOGIN_REQUIRED);
        }
        return new ResponseEntity<>(postService.getOnePost(postId), HttpStatus.valueOf(HttpStatus.FOUND.value()));
    }

    @ApiOperation(value = "게시물 수정", notes = "게시물 수정 기능", response = PostUpdateResponseDto.class)
    @PutMapping("/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto) {
        User user = tokenProvider.getUserFromAuthentication();
        if (tokenProvider.getUserFromAuthentication() == null) {
            throw new InvalidValueException(ErrorCode.LOGIN_REQUIRED);
        }
        return new ResponseEntity<>(postService.updateOnePost(postId, user, requestDto), HttpStatus.valueOf(HttpStatus.OK.value()));
    }

    @ApiOperation(value = "게시물 삭제", notes = "게시물 삭제 기능")
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        User user = tokenProvider.getUserFromAuthentication();
        if (tokenProvider.getUserFromAuthentication() == null) {
            throw new InvalidValueException(ErrorCode.LOGIN_REQUIRED);
        }
        postService.deleteOnePost(postId, user);
        return new ResponseEntity<>("This post is deleted.", HttpStatus.valueOf(HttpStatus.OK.value()));
    }
}
