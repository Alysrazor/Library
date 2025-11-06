package com.alysrazor.library.service;

import com.alysrazor.library.dto.BookDTO;
import com.alysrazor.library.entity.Book;

import java.util.List;

public interface BookService {
    List<BookDTO> findAll();

    BookDTO findById(int id);
    BookDTO findByIsbn(String isbn);

    List<BookDTO> findByTitleContainingIgnoreCase(String title);

    Book save(Book save);
    BookDTO update(int id, Book update);
    void delete(Book delete);
}
