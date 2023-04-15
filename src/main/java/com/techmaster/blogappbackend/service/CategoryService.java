package com.techmaster.blogappbackend.service;

import com.techmaster.blogappbackend.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getCategory();

    List<CategoryDto> getCategoryMostUsed(int top);
}
