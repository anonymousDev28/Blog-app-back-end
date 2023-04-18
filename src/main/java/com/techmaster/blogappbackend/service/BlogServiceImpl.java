package com.techmaster.blogappbackend.service;

import com.techmaster.blogappbackend.entity.Blog;
import com.techmaster.blogappbackend.repository.BlogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BlogServiceImpl implements BlogService{
    @Autowired
    BlogRepository blogRepository;
    @Autowired
    CategoryService categoryService;
    @Override
    public Page<Blog> getBlogs(int pageValue, int pageSizeValue) {
        Pageable pageable = PageRequest
                .of(pageValue, pageSizeValue);
        return blogRepository.findByStatus(true,pageable);
    }
    @Override
    public List<Blog> getBlogContain(String termValue) {
//        log.info("input key word: "+termValue);
//        return termValue!=null ? blogRepository.findByTitleContainingJPQL(termValue) : new ArrayList<>();
        return blogRepository.findByTitleContainingJPQL(termValue);
    }

    @Override
    public List<Blog> getBlogUsedCategory(String name) {
        return blogRepository.findByCategoriesNative(name);
    }

    @Override
    public Blog getByIdAndSlug(int blogID, String blogSlug) {
        return blogRepository.findByIdAndSlugAndStatusTrue(blogID,blogSlug);
    }

    @Override
    public List<Blog> getAllBlog() {
        return blogRepository.findAll();
    }
}
