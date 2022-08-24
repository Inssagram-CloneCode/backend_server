package com.clonecode.inssagram.repository;

import com.clonecode.inssagram.domain.Heart;
import com.clonecode.inssagram.domain.Post;
import com.clonecode.inssagram.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    Optional<Heart> findByPostAndUser(Post post, User user);
    Long countAllByPostAndIsHeart(Post post, Long isHeart);
}
