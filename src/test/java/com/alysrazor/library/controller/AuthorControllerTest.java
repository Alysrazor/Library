package com.alysrazor.library.controller;

import com.alysrazor.library.dto.AuthorDTO;
import com.alysrazor.library.dto.BookDTO;
import com.alysrazor.library.entity.Author;
import com.alysrazor.library.exceptions.AuthorHasBooksException;
import com.alysrazor.library.exceptions.AuthorNotFoundException;
import com.alysrazor.library.mapper.AuthorMapper;
import com.alysrazor.library.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AuthorController.class)
class AuthorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthorService service;

    @MockitoBean
    private AuthorMapper mapper;

    @Test
    void getAuthor_ShouldReturnAuthor_WhenExists() throws Exception {
        AuthorDTO dto = new AuthorDTO(
                1L, "George R. R. Martin",
                null, null, null, null, List.of()
        );

        when(service.findById(1)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/author/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("George R. R. Martin"));

        verify(service).findById(1);
    }

    @Test
    void getAuthor_ShouldThrowException_WhenDoesNotExists() throws Exception {
        AuthorDTO dto = new AuthorDTO(
                1L, "George R. R. Martin",
                null, null, null, null, List.of()
        );

        when(service.findById(2)).thenThrow(new AuthorNotFoundException(2));

        mockMvc.perform(get("/api/v1/author/2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.details").value("Author not found."));

        verify(service).findById(2);
    }

    @Test
    void getAuthor_ShouldThrowExceptionAuthor_WhenDelete() throws Exception {
        AuthorDTO dto = new AuthorDTO(
                1L, "George R. R. Martin",
                null, null, null, null,
                List.of(new BookDTO(
                        1L, "Book", null, 0,
                        null, null, null,
                        null, null
                ))
        );

        when(service.findById(1)).thenReturn(dto);
        when(mapper.toEntity(any(AuthorDTO.class))).thenReturn(new Author());
        doThrow(new AuthorHasBooksException(1)).when(service).delete(any(Author.class));

        mockMvc.perform(delete("/api/v1/author/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.details").value("Can't delete author."));

        verify(service).findById(1);
        verify(service).delete(any(Author.class));
    }
}