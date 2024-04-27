package com.lukaj.socialnetwork.repository;

import com.lukaj.socialnetwork.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Integer> {

}
