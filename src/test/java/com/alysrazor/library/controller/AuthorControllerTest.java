package com.alysrazor.library.controller;

import com.alysrazor.library.dto.AuthorDTO;
import com.alysrazor.library.entity.Author;
import com.alysrazor.library.mapper.AuthorMapper;
import com.alysrazor.library.service.AuthorService;
import com.alysrazor.library.service.JwtService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthorService authorService;

    @MockitoBean
    private AuthorMapper authorMapper;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @MockitoBean
    private AuthenticationProvider authProvider;

    @Test
    @WithAnonymousUser
    @DisplayName("Anonymous: Ok when getting authors.")
    void givenAnonymousUser_whenGettingAuthors_thenOk() throws Exception {
        when(authorService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/author"))
                .andExpect(status().isOk());

        verify(authorService).findAll();
        verifyNoMoreInteractions(authorService);
    }

    @Test
    @WithAnonymousUser
    @DisplayName("Anonymous: Ok when getting an author by Id.")
    void givenAnonymousUser_whenGettingAuthorById_thenOk() throws Exception {
        AuthorDTO dto = new AuthorDTO(
                1L, "Brandon Sanderson", null, null, null, null, null
        );

        when(authorService.findById(1)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/author/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Brandon Sanderson"));

        verify(authorService).findById(1);
        verifyNoMoreInteractions(authorService);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Admin: Created when creating authors.")
    void givenAdminUser_whenCreatingAuthors_thenCreated() throws Exception {
        AuthorDTO dto = new AuthorDTO(
                1L, "Brandon Sanderson", null, null, null, null, null
        );

        String body = new ObjectMapper().writeValueAsString(dto);

        Author entity = new Author();
        entity.setId(1);
        entity.setName("Brandon Sanderson");

        when(authorMapper.toEntity(dto)).thenReturn(entity);
        when(authorService.save(entity)).thenReturn(entity);
        when(authorMapper.toDTO(entity)).thenReturn(dto);

        mockMvc.perform(post("/api/v1/author")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Brandon Sanderson"));

        verify(authorService).save(entity);
        verifyNoMoreInteractions(authorService);
    }
}