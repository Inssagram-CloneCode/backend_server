package com.clonecode.inssagram.repository;

import com.clonecode.inssagram.domain.Post;
import com.clonecode.inssagram.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedAtDesc();

    List<Post> findAllByUserOrderByCreatedAtDesc(User user);

    Long countByUserId(Long userId);
    Post findByUserId(Long userId);

    List<Post> findAllByUserId(Long userId);
}
