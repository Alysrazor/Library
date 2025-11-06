package com.alysrazor.library.controller;

import com.alysrazor.library.dto.BookDTO;
import com.alysrazor.library.dto.PublisherDTO;
import com.alysrazor.library.entity.Publisher;
import com.alysrazor.library.mapper.PublisherMapper;
import com.alysrazor.library.service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/v1/publisher")
@RequiredArgsConstructor
public class PublisherController {
    private final PublisherService service;
    private final PublisherMapper mapper;

    @GetMapping({"", "/"})
    public ResponseEntity<List<PublisherDTO>> getAllPublishers(
            @RequestParam(required = false) String name
    ) {
        List<PublisherDTO> publisherList = (name == null || name.isBlank())
                ? service.findAll()
                : service.findByNameContainingIgnoreCase(name);

        return publisherList.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(publisherList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublisherDTO> getPublisherById(
            @PathVariable int id
    ) {
        PublisherDTO found = service.findById(id);

        return found == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(found);
    }

    @GetMapping("/books")
    public ResponseEntity<List<BookDTO>> getPublisherBooks(
            @RequestParam String name
    ) {
        List<BookDTO> bookList = service.findByNameContainingIgnoreCase(name)
                .getFirst().bookList();

        return bookList.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(bookList);
    }

    @PostMapping({"", "/"})
    public ResponseEntity<PublisherDTO> insertPublisher(
            @RequestBody PublisherDTO publisher
    ) {
        Publisher entity = mapper.toEntity(publisher);
        Publisher saved = service.save(entity);

        URI location = URI.create(
                String.format("/api/v1/publisher/%d", saved.getId())
        );

        return ResponseEntity.created(location)
                .body(mapper.toDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublisherDTO> updatePublisher(
            @PathVariable int id,
            @RequestBody Publisher publisher
    ) {
        return new ResponseEntity<>(
                service.update(id, publisher),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        Publisher delete = mapper.toEntity(service.findById(id));
        service.delete(delete);

        return ResponseEntity.noContent().build();
    }
}
