package com.clonecode.inssagram.repository;

import com.clonecode.inssagram.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
