package com.techmaster.blogappbackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "data",columnDefinition = "LONGBLOB")
    private byte[] data;

    @Column(name = "type")
    private String type;


    @Column(name = "created_at")
    private LocalDate created_at;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @PrePersist
    public void prePersist() {
        created_at = LocalDateTime.now().toLocalDate();
    }
}
