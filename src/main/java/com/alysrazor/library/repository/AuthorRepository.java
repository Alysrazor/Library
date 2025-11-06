package com.alysrazor.library.repository;

import com.alysrazor.library.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Optional<Author> findById(int id);
    Optional<Author> findByName(String name);
    Optional<List<Author>> findByNameContainingIgnoreCase(String name);
}
