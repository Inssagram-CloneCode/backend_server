package com.clonecode.inssagram.controller;

import com.clonecode.inssagram.dto.request.HeartRequestDto;
import com.clonecode.inssagram.dto.response.HeartResponseDto;
import com.clonecode.inssagram.service.HeartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@Api(tags = {"좋아요 API Controller"})
@RestController
@RequiredArgsConstructor
public class HeartController {
    private final HeartService heartService;

    @ApiOperation(value = "좋아요", notes = "좋아요 기능", response = HeartResponseDto.class)
    @PostMapping("/api/likes/{postId}")
    public ResponseEntity toggleHeart(@PathVariable(name = "postId") Long postId,
                                      @RequestBody HeartRequestDto requestDto) {
        return new ResponseEntity<>(
                heartService.toggleHeart(postId, requestDto),
                HttpStatus.valueOf(HttpStatus.OK.value()));
    }

}
