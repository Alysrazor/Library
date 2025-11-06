package com.alysrazor.library.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "book", catalog = "library")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Book implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(unique = true, nullable = false)
    private String isbn;

    @Column(nullable = false)
    private LocalDate publication_date;

    @Column(nullable = false)
    private int pages;

    @Column(nullable = false)
    private String language;

    @Column(columnDefinition = "LONGTEXT")
    private String summary;

    @Column(nullable = false)
    private String genre;

    @Column(name = "create_date", updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime create_date;

    @Column(name = "update_date",
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime update_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="author_id")
    @JsonBackReference("author-book")
    private Author author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="publisher_id")
    @JsonBackReference("publisher-book")
    private Publisher publisher;

    public Book(
            String title,
            String isbn,
            LocalDate publication_date,
            int pages,
            String language,
            String summary,
            String genre,
            Author author,
            Publisher publisher
    ) {
        this.title = title;
        this.isbn = isbn;
        this.publication_date = publication_date;
        this.pages = pages;
        this.language = language;
        this.summary = summary;
        this.genre = genre;
        this.author = author;
        this.publisher = publisher;
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
