package com.clonecode.inssagram.jwt;

import com.clonecode.inssagram.domain.RefreshToken;
import com.clonecode.inssagram.domain.User;
import com.clonecode.inssagram.domain.UserDetailsImpl;
import com.clonecode.inssagram.dto.TokenDto;
import com.clonecode.inssagram.dto.response.ResponseDto;
import com.clonecode.inssagram.global.error.ErrorCode;
import com.clonecode.inssagram.repository.RefreshTokenRepository;
import com.clonecode.inssagram.shared.Authority;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
public class TokenProvider {

  private static final String AUTHORITIES_KEY = "auth";
  private static final String BEARER_PREFIX = "Bearer ";
  private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;            //30분
  private static final long REFRESH_TOKEN_EXPRIRE_TIME = 1000 * 60 * 60 * 24 * 7;     //7일

  private final Key key;

  private final RefreshTokenRepository refreshTokenRepository;

  public TokenProvider(@Value("${jwt.secret}") String secretKey,
      RefreshTokenRepository refreshTokenRepository) {
    this.refreshTokenRepository = refreshTokenRepository;
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    this.key = Keys.hmacShaKeyFor(keyBytes);
  }

  //로그인용 토큰 Dto 생성 로직
  public TokenDto generateTokenDto(User user) {
    //시간
    long now = (new Date().getTime());

    //만료 시간 객체 생성
    Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);

    //액세스 토큰 생성
    //email, 권한, 만료시간 포함.
    String accessToken = Jwts.builder()
        .setSubject(user.getEmail())
        .claim(AUTHORITIES_KEY, Authority.ROLE_MEMBER.toString())
        .setExpiration(accessTokenExpiresIn)
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();

    //리프레시 토큰 생성(만료시간 포함)
    String refreshToken = Jwts.builder()
        .setExpiration(new Date(now + REFRESH_TOKEN_EXPRIRE_TIME))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();

    //DB저장하기 위한 토큰 Entity 생성
    RefreshToken refreshTokenObject = RefreshToken.builder()
        .id(user.getUserId())
        .user(user)
        .refreshTokenValue(refreshToken)
        .build();

    //토큰 저장
    refreshTokenRepository.save(refreshTokenObject);

    //로그인 Response용 토큰 DTO 생성
    return TokenDto.builder()
        .grantType(BEARER_PREFIX)
        .accessToken(accessToken)
        .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
        .refreshToken(refreshToken)
        .build();

  }

  //User 얻기위한 로직
  public User getUserFromAuthentication() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //인증이 되지 않았으면 null 리턴
    if (authentication == null || AnonymousAuthenticationToken.class.
        isAssignableFrom(authentication.getClass())) {
      return null;
    }
    //ContextHolder에서 User 리턴
    return ((UserDetailsImpl) authentication.getPrincipal()).getUser();
  }

  //토큰 만료 확인
  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (SecurityException | MalformedJwtException e) {
      log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
    } catch (ExpiredJwtException e) {
      log.info("Expired JWT token, 만료된 JWT token 입니다.");
    } catch (UnsupportedJwtException e) {
      log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
    } catch (IllegalArgumentException e) {
      log.info("JWT claims is empty, 로그인 정보가 없습니다.");
    }
    return false;
  }

  @Transactional(readOnly = true)
  public RefreshToken isPresentRefreshToken(User user) {
    Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByUser(user);
    return optionalRefreshToken.orElse(null);
  }

  @Transactional
  public ResponseDto<?> deleteRefreshToken(User user) {
    RefreshToken refreshToken = isPresentRefreshToken(user);
    if (null == refreshToken) {
      return ResponseDto.fail(ErrorCode.TOKEN_NOT_FOUND);
    }

    refreshTokenRepository.delete(refreshToken);
    return ResponseDto.success("success");
  }
}
