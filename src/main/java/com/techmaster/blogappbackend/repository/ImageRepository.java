package com.techmaster.blogappbackend.repository;

import com.techmaster.blogappbackend.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Integer> {
}