package com.techmaster.blogappbackend.service;

import com.techmaster.blogappbackend.dto.CategoryDto;
import com.techmaster.blogappbackend.entity.Category;
import com.techmaster.blogappbackend.repository.BlogRepository;
import com.techmaster.blogappbackend.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BlogRepository blogRepository;
    @Override
    public List<CategoryDto> getCategory() {
        ModelMapper modelMapper = new ModelMapper();
        List<CategoryDto> result = categoryRepository
                .findAll()
                .stream()
                .map(category -> modelMapper.map(category,CategoryDto.class))
                .collect(Collectors.toList());
        result.stream().forEach(categoryDto ->
                    categoryDto
                        .setUsed(
                                blogRepository.getBlogUsedCategory(categoryDto.getName())
                        ));
        return result;

    }

    @Override
    public List<CategoryDto> getTop5CategoryMostUsed() {
        return blogRepository.getTop5UsedCategories();
    }


}
