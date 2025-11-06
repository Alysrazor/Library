package com.alysrazor.library.repository;

import com.alysrazor.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findById(int id);
    Optional<List<Book>> findByTitleContainingIgnoreCase(String title);
    Optional<Book> findByIsbn(String isbn);
}
