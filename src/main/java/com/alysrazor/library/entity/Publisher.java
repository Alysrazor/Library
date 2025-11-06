package com.alysrazor.library.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "publisher", catalog = "library")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "bookList")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String website;

    private String email;

    @Column(nullable = false)
    private String address;

    @Column(name = "create_date", updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime create_date;

    @Column(name = "update_date",
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime update_date;

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonManagedReference("publisher-book")
    @Builder.Default
    private List<Book> bookList = new ArrayList<>();

    public Publisher(
            String name,
            String country,
            String website,
            String email,
            String address
    ) {
        this.name = name;
        this.country = country;
        this.website = website;
        this.email = email;
        this.address = address;
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
