package com.clonecode.inssagram.controller;

import com.clonecode.inssagram.dto.request.EditUserProfileRequestDto;
import com.clonecode.inssagram.dto.request.LoginRequestDto;
import com.clonecode.inssagram.dto.request.SignUpRequestDto;
import com.clonecode.inssagram.dto.response.LoginResponseDto;
import com.clonecode.inssagram.dto.response.ResponseDto;
import com.clonecode.inssagram.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Api(tags = {"유저 API Controller"})
@RequestMapping(path = "/api")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    @ApiOperation(value = "회원가입", notes = "회원가입 기능", response = ResponseDto.class)
    public ResponseEntity<?> signup(@RequestBody @Valid SignUpRequestDto requestDto) {
        return new ResponseEntity<>(userService.createUser(requestDto), HttpStatus.valueOf(HttpStatus.OK.value()));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "로그인", notes = "로그인 기능", response = LoginResponseDto.class)
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDto requestDto) {
        return new ResponseEntity<>(userService.login(requestDto), HttpStatus.valueOf(HttpStatus.OK.value()));
    }

    @RequestMapping(value = "/auth/logout", method = RequestMethod.GET)
    @ApiOperation(value = "로그아웃", notes = "로그아웃 기능", response = ResponseDto.class)
    public ResponseEntity<?> logout(HttpServletRequest request) {
        userService.logout(request);
        return new ResponseEntity<>("로그아웃", HttpStatus.valueOf(HttpStatus.OK.value()));
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.PUT)
    @ApiOperation(value = "유저 프로필 수정", notes = "유저 프로필 수정 기능")
    public ResponseEntity<?> editUserProfile(EditUserProfileRequestDto editUserProfileRequestDto,
                                             List<MultipartFile> profileImageFile, @PathVariable Long userId) {
        userService.editUserProfile(editUserProfileRequestDto, profileImageFile, userId);
        return new ResponseEntity<>("회원 정보 수정이 성공적으로 반영되었습니다", HttpStatus.valueOf(HttpStatus.OK.value()));
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ApiOperation(value = "유저 정보 재요청", notes = "유저 정보 재요청 기능", response = ResponseDto.class)
    public ResponseEntity<?> getUserProfile() {
        return new ResponseEntity<>(userService.getUserProfile(), HttpStatus.valueOf(HttpStatus.OK.value()));
    }

    @RequestMapping(value = "/users/reload", method = RequestMethod.GET)
    @ApiOperation(value = "액세스 토큰 재발급", notes = "액세스 토큰 재발급 기능", response = ResponseDto.class)
    public ResponseEntity<?> reload(HttpServletRequest request) {
        return new ResponseEntity<>(userService.reload(request), HttpStatus.valueOf(HttpStatus.OK.value()));
    }
}
