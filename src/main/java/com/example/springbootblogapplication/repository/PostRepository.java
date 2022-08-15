package com.example.springbootblogapplication.repository;

import com.example.springbootblogapplication.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
