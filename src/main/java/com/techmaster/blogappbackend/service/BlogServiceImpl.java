package com.techmaster.blogappbackend.service;

import com.techmaster.blogappbackend.entity.Blog;
import com.techmaster.blogappbackend.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogServiceImpl implements BlogService{
    @Autowired
    BlogRepository blogRepository;
    @Autowired
    CategoryService categoryService;
    @Override
    public Page<Blog> getBlogs(int pageValue, int pageSizeValue) {
        Pageable pageable = PageRequest
                .of(pageValue, pageSizeValue);
        return blogRepository.findAll(pageable);
    }
    @Override
    public List<Blog> getBlogContain(String termValue) {
        return blogRepository.findByTitleContainingJPQL(termValue);
    }

    @Override
    public List<Blog> getBlogUsedCategory(String name) {
        return blogRepository.findByCategoriesNative(name);
    }

    @Override
    public Blog getByIdAndSlug(int blogID, String blogSlug) {
        return blogRepository.findByIdAndSlug(blogID,blogSlug);
    }

    @Override
    public List<Blog> getAllBlog() {
        return blogRepository.findAll();
    }
}
