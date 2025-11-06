package com.alysrazor.library.controller;

import com.alysrazor.library.dto.BookDTO;
import com.alysrazor.library.dto.PublisherDTO;
import com.alysrazor.library.entity.Publisher;
import com.alysrazor.library.exceptions.PublisherHasBooksException;
import com.alysrazor.library.exceptions.PublisherNotFoundException;
import com.alysrazor.library.mapper.PublisherMapper;
import com.alysrazor.library.service.PublisherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PublisherController.class)
public class PublisherControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PublisherMapper mapper;

    @MockitoBean
    private PublisherService service;

    @Test
    void getPublisher_ShouldReturnPublisher_WhenExists() throws Exception {
        PublisherDTO dto = new PublisherDTO(
                1L, "Tor Books", null,
                null, null, null,
                List.of()
        );

        when(service.findById(1)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/publisher/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Tor Books"));

        verify(service).findById(1);
    }

    @Test
    void getPublisher_ShouldThrowException_WhenDoesNotExists() throws Exception {
        PublisherDTO dto = new PublisherDTO(
                1L, "Tor Books", null,
                null, null, null,
                List.of()
        );

        when(service.findById(2)).thenThrow(new PublisherNotFoundException(2));

        mockMvc.perform(get("/api/v1/publisher/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.details").value("Publisher not found."));

        verify(service).findById(2);
    }

    @Test
    void getPublisher_ShouldThrowExceptionAuthor_WhenDelete() throws Exception {
        PublisherDTO dto = new PublisherDTO(
                1L, "Tor Books", null,
                null, null, null,
                List.of(new BookDTO(
                        1L, "Book", null, 0,
                        null, null, null,
                        null, null
                ))
        );

        when(service.findById(1)).thenReturn(dto);
        when(mapper.toEntity(any(PublisherDTO.class))).thenReturn(new Publisher());
        doThrow(new PublisherHasBooksException(1)).when(service).delete(any(Publisher.class));

        mockMvc.perform(delete("/api/v1/publisher/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.details").value("Can't delete publisher."));

        verify(service).findById(1);
        verify(service).delete(any(Publisher.class));
    }
}
