package com.alysrazor.library.controller;

import com.alysrazor.library.annotation.IsAdmin;
import com.alysrazor.library.annotation.IsUser;
import com.alysrazor.library.dto.AuthorDTO;
import com.alysrazor.library.dto.BookDTO;
import com.alysrazor.library.entity.Author;
import com.alysrazor.library.mapper.AuthorMapper;
import com.alysrazor.library.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/v1/author")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorMapper mapper;
    private final AuthorService service;

    @GetMapping({"", "/"})
    public ResponseEntity<List<AuthorDTO>> getAllAuthors(
            @RequestParam(required = false) String name
    ) {
        List<AuthorDTO> authorList = (name == null || name.isBlank())
                ? service.findAll()
                : service.findByNameContainingIgnoreCase(name);

        return authorList.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(authorList);
    }

    @GetMapping({"/books"})
    public ResponseEntity<List<BookDTO>> getAuthorBooks(
            @RequestParam String name
    ) {
        List<BookDTO> bookList = service.findByNameContainingIgnoreCase(name).getFirst().bookList();

        return bookList.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(bookList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(
            @PathVariable int id
    ) {
        AuthorDTO author = service.findById(id);

        return author == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(author);
    }


    @PostMapping({"", "/"})
    @IsAdmin
    public ResponseEntity<AuthorDTO> insertAuthor(
             @RequestBody AuthorDTO author
    ) {
        Author entity = mapper.toEntity(author);
        Author saved = service.save(entity);

        URI location = URI.create(
                String.format("/api/vi/author/%d", saved.getId())
        );

        return ResponseEntity.created(location).body(mapper.toDTO(saved));
    }

    @PutMapping("/{id}")
    @IsAdmin
    public ResponseEntity<AuthorDTO> updateAuthor(
            @PathVariable int id,
            @RequestBody Author author
    ) {
        return new ResponseEntity<>(service.update(id, author), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @IsAdmin
    public ResponseEntity<?> delete(@PathVariable int id) {
        Author delete = mapper.toEntity(service.findById(id));
        service.delete(delete);

        return ResponseEntity.noContent().build();
    }
}
