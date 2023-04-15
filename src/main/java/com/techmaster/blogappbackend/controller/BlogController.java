package com.techmaster.blogappbackend.controller;

import com.techmaster.blogappbackend.dto.CategoryDto;
import com.techmaster.blogappbackend.entity.Blog;

import com.techmaster.blogappbackend.service.BlogService;
import com.techmaster.blogappbackend.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "api/v1/public")
public class BlogController {
    @Autowired
    BlogService blogService;
    @Autowired
    CategoryService categoryService;
    // get list controller have paging
    @GetMapping("blogs")
    public Page<Blog> getBlogs(
            @RequestParam(value = "page",required = false) int pageValue,
            @RequestParam(value = "pageSize",required = false)  int pageSizeValue
    ){
        return blogService.getBlogs(pageValue,pageSizeValue);
    }
    @GetMapping("search")
    public List<Blog> searchBlog(
            @RequestParam(value = "term") String termValue
    ){
        return blogService
                .getBlogContain(termValue);
    }
    @GetMapping("categories")
    public List<CategoryDto> getCategories(
    ){
        return categoryService.getCategory();
    }

    @GetMapping("category/{top}")
    public List<CategoryDto> getCategoryMostUsed(@PathVariable(name = "top") int top){
        return categoryService.getCategoryMostUsed(top);
    }
    @GetMapping("category/{categoryName}")
    public List<Blog> getBlogUsedCategory(@PathVariable(name = "categoryName") String name){
        return blogService.getBlogUsedCategory(name);
    }
    @GetMapping("blogs/{blogId}/{blogSlug}")
    public ResponseEntity<?> getBlogByIdAndSlug(
            @PathVariable(name = "blogId") int blogID,
            @PathVariable(name = "blogSlug") String blogSlug
    ){
        Blog blog = blogService.getByIdAndSlug(blogID,blogSlug);
        return blog != null ? ResponseEntity.ok(blog) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD REQUEST");
    }

}
