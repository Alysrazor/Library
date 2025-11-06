package com.alysrazor.library.controller;

import com.alysrazor.library.dto.*;
import com.alysrazor.library.entity.Author;
import com.alysrazor.library.entity.Book;
import com.alysrazor.library.entity.Publisher;
import com.alysrazor.library.exceptions.AuthorNotFoundException;
import com.alysrazor.library.exceptions.BookIsbnAlreadyExists;
import com.alysrazor.library.exceptions.BookNotFoundException;
import com.alysrazor.library.exceptions.PublisherNotFoundException;
import com.alysrazor.library.mapper.BookMapper;
import com.alysrazor.library.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BookController.class)
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService service;

    @MockitoBean
    private BookMapper mapper;

    @Test
    void getBook_ShouldReturnBook_WhenExists() throws Exception {
        BookDTO dto = new BookDTO(
                1L, "My Book", null, 0,
                null, null, null,
                null, null
        );

        when(service.findById(1)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/book/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("My Book"));

        verify(service).findById(1);
        verifyNoMoreInteractions(service);
    }

    @Test
    void getBook_ShouldThrowException_WhenDoesNotExists() throws Exception {
        BookDTO dto = new BookDTO(
                1L, "My Book", null, 0,
                null, null, null,
                null, null
        );

        when(service.findById(2)).thenThrow(new BookNotFoundException(2));

        mockMvc.perform(get("/api/v1/book/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.details").value("Book not found."));

        verify(service).findById(2);
        verifyNoMoreInteractions(service);
    }

    @Test
    void addBook_ShouldThrowException_WhenIsbnAlreadyExists() throws Exception {
        Book entity = new Book();

        entity.setId(1);
        entity.setIsbn("ISBN");

        when(mapper.toEntity(any(BookDTO.class))).thenReturn(entity);
        when(service.save(entity)).thenThrow(new BookIsbnAlreadyExists("ISBN"));

        mockMvc.perform(post("/api/v1/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "id": 1,
                                    "title": "My Book",
                                    "isbn": "ISBN",
                                    "pages": 1,
                                    "language": "Spanish",
                                    "genre": "",
                                    "publication_date": "2025-11-06",
                                    "author": {"id":2, "name": "Author"},
                                    "publisher": {"id":1, "name": "Publisher"}
                                }
                                """))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.details").value("ISBN already exists."));

        verify(service).save(entity);
        verifyNoMoreInteractions(service);
    }

    @Test
    void addBook_ShouldThrowException_WhenAuthorDoesNotExists() throws Exception {
        Book entity = new Book();

        when(mapper.toEntity(any(BookDTO.class))).thenReturn(entity);
        when(service.save(entity)).thenThrow(new AuthorNotFoundException(2));

        mockMvc.perform(post("/api/v1/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "id": 1,
                                    "title": "My Book",
                                    "isbn": "",
                                    "pages": 1,
                                    "language": "Spanish",
                                    "genre": "",
                                    "publication_date": "2025-11-06",
                                    "author": {"id":2, "name": "Author"},
                                    "publisher": {"id":1, "name": "Publisher"}
                                }
                                """))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.details").value("Author not found."));

        verify(service).save(entity);
        verifyNoMoreInteractions(service);
    }

    @Test
    void addBook_ShouldThrowException_WhenPublisherDoesNotExists() throws Exception {
        Book entity = new Book();

        when(mapper.toEntity(any(BookDTO.class))).thenReturn(entity);
        when(service.save(entity)).thenThrow(new PublisherNotFoundException(2));

        mockMvc.perform(post("/api/v1/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "id": 1,
                                    "title": "My Book",
                                    "isbn": "",
                                    "pages": 1,
                                    "language": "Spanish",
                                    "genre": "",
                                    "publication_date": "2025-11-06",
                                    "author": {"id":1, "name": "Author"},
                                    "publisher": {"id":2, "name": "Publisher"}
                                }
                                """))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.details").value("Publisher not found."));

        verify(service).save(entity);
        verifyNoMoreInteractions(service);
    }

    @Test
    void updateBook_ShouldThrowException_WhenAuthorDoesNotExists() throws Exception {
        Book book = new Book();

        book.setId(1);
        book.setAuthor(new Author());

        when(service.update(eq(1), any(Book.class))).thenThrow(new AuthorNotFoundException(2));

        mockMvc.perform(put("/api/v1/book/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "author": {"id":2, "name": "Author"}
                                }
                                """))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.details").value("Author not found."));

        verify(service).update(eq(1), any(Book.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void updateBook_ShouldThrowException_WhenPublisherDoesNotExists() throws Exception {
        Book book = new Book();

        book.setId(1);
        book.setPublisher(new Publisher());

        when(service.update(eq(1), any(Book.class))).thenThrow(new PublisherNotFoundException(2));

        mockMvc.perform(put("/api/v1/book/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "publisher": {"id":2, "name": "Publisher"}
                                }
                                """))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.details").value("Publisher not found."));

        verify(service).update(eq(1), any(Book.class));
        verifyNoMoreInteractions(service);
    }
}
