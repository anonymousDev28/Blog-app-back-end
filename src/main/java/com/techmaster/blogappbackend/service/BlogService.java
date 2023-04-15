package com.techmaster.blogappbackend.service;

import com.techmaster.blogappbackend.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogService {

    

    Page<Blog> getBlogs(int pageValue, int pageSizeValue);

    List<Blog> getBlogContain(String termValue);

    List<Blog> getBlogUsedCategory(String name);

    Blog getByIdAndSlug(int blogID, String blogSlug);
}
