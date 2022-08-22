package com.clonecode.inssagram.repository;

import com.clonecode.inssagram.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
