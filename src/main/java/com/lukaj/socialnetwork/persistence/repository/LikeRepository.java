package com.lukaj.socialnetwork.persistence.repository;

import com.lukaj.socialnetwork.persistence.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<LikeEntity, Integer> {
}
