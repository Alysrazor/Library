package com.alysrazor.library.service;

import com.alysrazor.library.dto.AuthorDTO;
import com.alysrazor.library.entity.Author;

import java.util.List;

public interface AuthorService {
    List<AuthorDTO> findAll();
    AuthorDTO findById(int id);
    List<AuthorDTO> findByNameContainingIgnoreCase(String name);

    Author save(Author save);
    AuthorDTO update(int id, Author update);
    void delete(Author delete);
}
