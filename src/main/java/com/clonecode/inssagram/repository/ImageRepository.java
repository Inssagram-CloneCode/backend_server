package com.clonecode.inssagram.repository;

import com.clonecode.inssagram.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
