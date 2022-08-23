package com.clonecode.inssagram.repository;

import com.clonecode.inssagram.domain.RefreshToken;
import com.clonecode.inssagram.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUser(User user);

    Optional<RefreshToken> findByRefreshTokenValue(String refreshToken);
}
