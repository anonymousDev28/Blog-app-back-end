package com.techmaster.blogappbackend.repository;

import com.techmaster.blogappbackend.dto.CategoryDto;
import com.techmaster.blogappbackend.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog, String> {
    Page<Blog> findByStatus(boolean status,Pageable pageable);
    @Query("SELECT b FROM Blog b WHERE b.title LIKE CONCAT('%', :keyword, '%') and b.status = true")
    List<Blog> findByTitleContainingJPQL(@Param("keyword") String keyword);

    @Query("SELECT NEW com.techmaster.blogappbackend.dto.CategoryDto(c.id, c.name,COUNT(b)) " +
            "FROM Blog b " +
            "JOIN b.categories c " +
            "WHERE b.status = true "+
            "GROUP BY c.id, c.name " +
            "ORDER BY COUNT(b) DESC")
    List<CategoryDto> getTop5UsedCategories(Pageable pageable);

    @Query(value = "SELECT b.* FROM blog b INNER JOIN blogs_categories bc ON b.id = bc.blog_id INNER JOIN category c ON bc.category_id = c.id WHERE c.name = ? and b.status = true", nativeQuery = true)
    List<Blog> findByCategoriesNative(String name);
    @Query(value = "SELECT count(*) FROM blog b INNER JOIN blogs_categories bc ON b.id = bc.blog_id INNER JOIN category c ON bc.category_id = c.id WHERE c.name = ? and b.status = true", nativeQuery = true)
    Long getBlogUsedCategory(String name);

    Blog findByIdAndSlugAndStatusTrue(int blogID, String blogSlug);
}