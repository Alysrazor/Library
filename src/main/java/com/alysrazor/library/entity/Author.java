package com.alysrazor.library.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "author", catalog = "library")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "bookList")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private String nationality;

    private String email;
    private String website;

    @Column(name = "create_date", updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime create_date;

    @Column(name = "update_date",
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime update_date;

    @OneToMany(mappedBy = "author", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonManagedReference("author-book")
    @Builder.Default
    private List<Book> bookList = new ArrayList<>();

    public Author(
            String name,
            String nationality,
            LocalDate birthDate,
            String email,
            String website
    ) {
        this.name = name;
        this.nationality = nationality;
        this.birthDate = birthDate;
        this.email = email;
        this.website = website;
        this.bookList = new ArrayList<>();
    }

    @PrePersist
    protected void onCreate() {
        this.create_date = LocalDateTime.now();
        this.update_date = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.update_date = LocalDateTime.now();
    }
}
