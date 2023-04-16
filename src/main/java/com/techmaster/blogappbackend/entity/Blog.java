package com.techmaster.blogappbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "blog")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "slug", nullable = false)
    private String slug;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private  LocalDate updatedAt;

    @Column(name = "pulished_at")
    private  LocalDate pulishedAt;

    @Column(name = "status")
    private boolean status;
//    @JsonIgnoreProperties(ignoreUnknown = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "blogs_categories",
            joinColumns = @JoinColumn(name = "blog_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "blog", fetch = FetchType.LAZY)
    private List<Comment> comments;

    @PrePersist
    public void prePersist() {
        createdAt =  LocalDateTime.now().toLocalDate();
        if(status) {
            pulishedAt = createdAt;
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt =  LocalDateTime.now().toLocalDate();
        if(status) {
            pulishedAt = updatedAt;
        }
    }
}
