package com.techmaster.blogappbackend.service;

import com.techmaster.blogappbackend.dto.CategoryDto;
import com.techmaster.blogappbackend.repository.BlogRepository;
import com.techmaster.blogappbackend.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        return categoryRepository
                .findAll()
                .stream()
                .map(category -> modelMapper.map(category,CategoryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> getCategoryMostUsed(int top) {
        Pageable pageable = PageRequest.of(0,top);
        return blogRepository.getTopNUsedCategories(pageable);
    }
}
