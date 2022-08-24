package com.clonecode.inssagram.controller;

import com.clonecode.inssagram.dto.response.MyPageResponseDto;
import com.clonecode.inssagram.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
@Api(tags = {"마이페이지 API Controller"})
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class MyPageController {
    private final PostService postService;

    @ApiOperation(value = "마이페이지 조회", notes = "마이페이지 조회 기능", response = MyPageResponseDto.class)
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity getMyPage(@PathVariable(name = "userId") Long userId) {
        return new ResponseEntity<>(
                postService.getMyPage(userId),
                HttpStatus.valueOf(HttpStatus.OK.value()));
    }
}
