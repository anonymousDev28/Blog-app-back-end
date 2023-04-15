package com.techmaster.blogappbackend.repository;

import com.techmaster.blogappbackend.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}