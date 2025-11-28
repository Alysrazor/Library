package com.alysrazor.library.controller;

import com.alysrazor.library.config.JwtAuthFilter;
import com.alysrazor.library.config.SecurityConfig;
import com.alysrazor.library.dto.AuthorDTO;
import com.alysrazor.library.dto.BookDTO;
import com.alysrazor.library.entity.Author;
import com.alysrazor.library.exception.AuthorHasBooksException;
import com.alysrazor.library.mapper.AuthorMapper;
import com.alysrazor.library.repository.TokenRepository;
import com.alysrazor.library.repository.UserRepository;
import com.alysrazor.library.service.AuthorService;
import com.alysrazor.library.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AuthorController.class)
@Import({ SecurityConfig.class, JwtAuthFilter.class })
class AuthorControllerWithSecurityTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthorService authorService;

    @MockitoBean
    private AuthorMapper authorMapper;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private AuthenticationProvider authProvider;

    @MockitoBean
    private UserRepository userRepo;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @MockitoBean
    private TokenRepository tokenRepo;

    @Test
    void anonymousUser_whenGettingAuthors_thenOk() throws Exception {
        mockMvc.perform(get("/api/v1/author"))
                .andExpect(status().isOk());
    }

    @Test
    void anonymousUser_whenGettingAnAuthor_thenOk() throws Exception {
        AuthorDTO dto = new AuthorDTO(
                1L, "George R. R. Martin",
                null, null, null, null, List.of()
        );

        when(authorService.findById(1)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/author/1"))
                .andExpect(status().isOk());

        verify(authorService).findById(1);
        verifyNoMoreInteractions(authorService);
    }

    @Test
    void anonymousUser_whenCreatingAnAuthor_thenForbidden() throws Exception {
        mockMvc.perform(post("/api/v1/author/"))
                .andExpect(status().isForbidden());
    }

    @Test
    void anonymousUser_whenUpdatingAnAuthor_thenForbidden() throws Exception {
        mockMvc.perform(put("/api/v1/author/**"))
                .andExpect(status().isForbidden());
    }

    @Test
    void anonymousUser_whenDeletingAnAuthor_thenForbidden() throws Exception {
        mockMvc.perform(delete("/api/v1/author/**"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    void regularUser_whenGettingAuthors_thenOk() throws Exception {
        mockMvc.perform(get("/api/v1/author/"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    void regularUser_whenGettingAnAuthor_thenOk() throws Exception {
        AuthorDTO dto = new AuthorDTO(
                1L, "George R. R. Martin",
                null, null, null, null, List.of()
        );

        when(authorService.findById(1)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/author/1"))
                .andExpect(status().isOk());

        verify(authorService).findById(1);
        verifyNoMoreInteractions(authorService);
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    void regularUser_whenCreatingAnAuthor_thenForbidden() throws Exception {
        mockMvc.perform(post("/api/v1/author/**"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    void regularUser_whenUpdatingAnAuthor_thenForbidden() throws Exception {
        mockMvc.perform(put("/api/v1/author/**"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    void regularUser_whenDeletingAnAuthor_thenForbidden() throws Exception {
        mockMvc.perform(delete("/api/v1/author/**"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void adminUser_whenGettingAuthors_thenOk() throws Exception {
        mockMvc.perform(get("/api/v1/author/"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void adminUser_whenGettingAnAuthor_thenOk() throws Exception {
        AuthorDTO dto = new AuthorDTO(
                1L, "George R. R. Martin",
                null, null, null, null, List.of()
        );

        when(authorService.findById(1)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/author/1"))
                .andExpect(status().isOk());

        verify(authorService).findById(1);
        verifyNoMoreInteractions(authorService);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void adminUser_whenCreatingAnAuthor_thenCreated() throws Exception {
        AuthorDTO dto = new AuthorDTO(
                1L, "George R. R. Martin",
                null, null, null, null, List.of()
        );

        String body = new ObjectMapper().writeValueAsString(dto);

        Author entity = new Author();
        entity.setId(1);
        entity.setName("George R. R. Martin");

        when(authorMapper.toEntity(any(AuthorDTO.class))).thenReturn(entity);
        when(authorService.save(any(Author.class))).thenReturn(entity);
        when(authorMapper.toDTO(any(Author.class))).thenReturn(dto);

        mockMvc.perform(post("/api/v1/author/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("George R. R. Martin"));

        verify(authorService).save(any(Author.class));
        verifyNoMoreInteractions(authorService);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void adminUser_whenUpdatingAnAuthor_thenCreated() throws Exception {
        AuthorDTO dto = new AuthorDTO(
                1L, "George R. R. Martin Updated",
                null, null, null, null, List.of()
        );

        String body = new ObjectMapper().writeValueAsString(dto);

        Author entity = new Author();
        entity.setId(1);
        entity.setName("George R. R. Martin Updated");

        when(authorService.update(eq(1), any(Author.class))).thenReturn(dto);

        mockMvc.perform(put("/api/v1/author/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("George R. R. Martin Updated"));

        verify(authorService).update(eq(1), any(Author.class));
        verifyNoMoreInteractions(authorService);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void adminUser_whenDeleteAnAuthorHasBooks_thenException() throws Exception {
        AuthorDTO dto = new AuthorDTO(
                1L, "George R. R. Martin Updated",
                null, null, null, null,
                List.of(new BookDTO(
                        1L, "Book", null, 0,
                        null, null, null,
                        null, null
                ))
        );

        when(authorService.findById(1)).thenReturn(dto);
        when(authorMapper.toEntity(any(AuthorDTO.class))).thenReturn(new Author());
        doThrow(new AuthorHasBooksException(1)).when(authorService).delete(any(Author.class));

        mockMvc.perform(delete("/api/v1/author/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.details").value("Can't delete author."));

        verify(authorService).findById(1);
        verify(authorService).delete(any(Author.class));
        verifyNoMoreInteractions(authorService);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void adminUser_whenDeleteAnAuthor_thenNoContent() throws Exception {
        AuthorDTO dto = new AuthorDTO(
                1L, "George R. R. Martin Updated",
                null, null, null, null,
                List.of()
        );

        when(authorService.findById(1)).thenReturn(dto);
        when(authorMapper.toEntity(any(AuthorDTO.class))).thenReturn(new Author());

        mockMvc.perform(delete("/api/v1/author/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(authorService).findById(1);
        verify(authorService).delete(any(Author.class));
        verifyNoMoreInteractions(authorService);
    }
}