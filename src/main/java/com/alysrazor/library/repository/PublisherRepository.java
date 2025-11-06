package com.alysrazor.library.repository;

import com.alysrazor.library.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Integer> {
    Optional<Publisher> findById(int id);
    Optional<List<Publisher>> findByNameContainingIgnoreCase(String name);
}
