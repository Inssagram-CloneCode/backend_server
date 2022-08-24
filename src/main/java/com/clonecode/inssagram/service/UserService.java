package com.clonecode.inssagram.service;

import com.clonecode.inssagram.domain.User;
import com.clonecode.inssagram.dto.TokenDto;
import com.clonecode.inssagram.dto.request.EditUserProfileRequestDto;
import com.clonecode.inssagram.dto.request.LoginRequestDto;
import com.clonecode.inssagram.dto.request.SignUpRequestDto;
import com.clonecode.inssagram.dto.response.LoginResponseDto;
import com.clonecode.inssagram.dto.response.ResponseDto;
import com.clonecode.inssagram.exception.InvalidValueException;
import com.clonecode.inssagram.global.error.ErrorCode;
import com.clonecode.inssagram.jwt.TokenProvider;
import com.clonecode.inssagram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;

    private final StorageService storageService;

    @Transactional
    public ResponseDto<?> createUser(SignUpRequestDto requestDto) {
        //같은 이메일을 사용하는 사람이 있는지
        if (null != isPresentUser(requestDto.getEmail())) {
            return ResponseDto.fail(ErrorCode.DUPLICATED_EMAIL);
        }

        //비밀번호 확인과정
        if (!requestDto.getPassword().equals(requestDto.getPasswordCheck())) {
            return ResponseDto.fail(ErrorCode.PASSWORD_NOT_MATCHED);
        }

        //User 생성 후 DB 저장
        User user = User.builder()
                .email(requestDto.getEmail())
                .username(requestDto.getUsername())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .build();
        userRepository.save(user);
        return ResponseDto.success("회원가입에 성공하였습니다.");
    }

    @Transactional
    public ResponseDto<?> login(LoginRequestDto requestDto) {
        //유저 이메일 존재 체크
        User user = isPresentUser(requestDto.getEmail());

        //유저 없을시 익셉션 처리
        if (null == user) {
            return ResponseDto.fail(ErrorCode.USER_NOT_FOUND);
        }

        //비밀번호 체크, 실패시 익셉션 처리
        if (!user.validatePassword(passwordEncoder, requestDto.getPassword())) {
            return ResponseDto.fail(ErrorCode.INVALID_USER);
        }

        // 토큰 Dto 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(user);

        //로그인DTO 리턴
        return ResponseDto.success(
                LoginResponseDto.builder()
                        .userId(user.getId())
                        .email(user.getEmail())
                        .username(user.getUsername())
                        .profileImgUrl(user.getProfileImageUrl())
                        .token(tokenDto)
                        .build()
        );
    }

    //로그아웃
    public ResponseDto<?> logout(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN);
        }
        User user = tokenProvider.getUserFromAuthentication();
        if (null == user) {
            return ResponseDto.fail(ErrorCode.NOT_LOGIN_STATE);
        }

        return tokenProvider.deleteRefreshToken(user);
    }

    @Transactional(readOnly = true)
    public User isPresentUser(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        return optionalUser.orElse(null);
    }

    public ResponseDto<?> getUserProfile() {
        //유저 이메일 존재 체크
        User loginUser = tokenProvider.getUserFromAuthentication();

        return ResponseDto.success(
                LoginResponseDto.builder()
                        .userId(loginUser.getId())
                        .email(loginUser.getEmail())
                        .username(loginUser.getUsername())
                        .profileImgUrl(loginUser.getProfileImageUrl())
                        .build());
    }

    @Transactional
    public ResponseDto<?> reload(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return ResponseDto.fail(ErrorCode.INVALID_TOKEN);
        }
        User userFromRefreshTokenRepo = tokenProvider.getUserFromRefreshTokenRepo(request.getHeader("Refresh-Token"));
        // 토큰 재발행
        TokenDto tokenDto = tokenProvider.generateTokenDto(userFromRefreshTokenRepo);

        return ResponseDto.success(tokenDto);
    }

    @Transactional
    public void editUserProfile(EditUserProfileRequestDto editUserProfileRequestDto, List<MultipartFile> profileImageFile, Long userId) {
        //S3 버킷에 이미지 저장 후 url 받아옴.
        String userProfileImageUrl = storageService.uploadFile(profileImageFile, "userProfile/").get(0);
        //영속성 컨텍스트 User 넣음
        User user = userRepository.findById(userId).orElseThrow(() -> new InvalidValueException(ErrorCode.USER_NOT_FOUND));
        
        //영속성 컨텍스트 update 변경
        user.update(editUserProfileRequestDto, userProfileImageUrl);
        // Transaction 끝날 때 컨텍스트 스탭샷과 비교하여 변경 감지 후 flush되며 update 쿼리 나감.
    }
}
