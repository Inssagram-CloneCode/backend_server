package com.clonecode.inssagram.controller;

import com.clonecode.inssagram.dto.request.LoginRequestDto;
import com.clonecode.inssagram.dto.request.SignUpRequestDto;
import com.clonecode.inssagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @RequestMapping(value = "/api/signup", method = RequestMethod.POST)
    public ResponseEntity<?> signup(@RequestBody @Valid SignUpRequestDto requestDto) {
        return new ResponseEntity<>(userService.createUser(requestDto), HttpStatus.valueOf(HttpStatus.OK.value()));
    }

    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDto requestDto,
                                HttpServletResponse response
    ) {
        return new ResponseEntity<>(userService.login(requestDto, response), HttpStatus.valueOf(HttpStatus.OK.value()));
    }

    @RequestMapping(value = "/api/auth/logout", method = RequestMethod.POST)
    public ResponseEntity<?> logout(HttpServletRequest request) {

        return new ResponseEntity<>("로그아웃", HttpStatus.valueOf(HttpStatus.OK.value()));
    }
}
