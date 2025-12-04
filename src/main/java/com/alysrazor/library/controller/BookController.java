package com.alysrazor.library.controller;

import com.alysrazor.library.annotation.IsAdmin;
import com.alysrazor.library.dto.BookDTO;
import com.alysrazor.library.entity.Book;
import com.alysrazor.library.mapper.BookMapper;
import com.alysrazor.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
public class BookController {
    private final BookMapper mapper;
    private final BookService service;

    @GetMapping({"", "/"})
    public ResponseEntity<List<BookDTO>> getAllBooks(
            @RequestParam(required = false) String title
    ) {
        List<BookDTO> bookList = (title == null || title.isBlank())
                ? service.findAll()
                : service.findByTitleContainingIgnoreCase(title);

        return ResponseEntity.ok(bookList);
    }

    @GetMapping("{id}")
    public ResponseEntity<BookDTO> getBookById(
            @PathVariable int id
    ) {
        BookDTO found = service.findById(id);

        return found == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(found);
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookDTO> getBookByISBN(
            @PathVariable String isbn
    ) {
        BookDTO found = service.findByIsbn(isbn);

        return found == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(found);
    }

    @PostMapping({"", "/"})
    @IsAdmin
    public ResponseEntity<BookDTO> insertBook(
            @RequestBody BookDTO book
    ) {
        Book entity = mapper.toEntity(book);
        Book saved = service.save(entity);

        URI location = URI.create(
                String.format("/api/v1/book/%d", saved.getId())
        );

        return ResponseEntity.created(location)
                .body(mapper.toDTO(saved));
    }

    @PutMapping("/{id}")
    @IsAdmin
    public ResponseEntity<BookDTO> updateBook(
            @PathVariable int id,
            @RequestBody Book book
    ) {
        return new ResponseEntity<>(service.update(id, book),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @IsAdmin
    public ResponseEntity<?> delete(@PathVariable int id) {
        Book delete = mapper.toEntity(service.findById(id));
        service.delete(delete);

        return ResponseEntity.noContent().build();
    }
}
