package com.clonecode.inssagram.repository;

import com.clonecode.inssagram.domain.Comment;
import com.clonecode.inssagram.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    int countByPost(Post post);

    List<Comment> findAllByPostIdOrderByCreatedAtDesc(Long id);
}
