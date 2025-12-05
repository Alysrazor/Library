package com.alysrazor.library.controller;

import com.alysrazor.library.config.JwtAuthFilter;
import com.alysrazor.library.config.SecurityConfig;
import com.alysrazor.library.dto.AuthorSummaryDTO;
import com.alysrazor.library.dto.BookDTO;
import com.alysrazor.library.dto.PublisherSummaryDTO;
import com.alysrazor.library.entity.Book;
import com.alysrazor.library.mapper.AuthorMapper;
import com.alysrazor.library.mapper.BookMapper;
import com.alysrazor.library.mapper.PublisherMapper;
import com.alysrazor.library.repository.TokenRepository;
import com.alysrazor.library.repository.UserRepository;
import com.alysrazor.library.service.AuthorService;
import com.alysrazor.library.service.BookService;
import com.alysrazor.library.service.JwtService;
import com.alysrazor.library.service.PublisherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookController.class)
@Import({SecurityConfig.class, JwtAuthFilter.class})
public class BookControllerWithSecurityTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @MockitoBean
    private BookMapper bookMapper;

    @MockitoBean
    private AuthorService authorService;

    @MockitoBean
    private AuthorMapper authorMapper;

    @MockitoBean
    private PublisherService publisherService;

    @MockitoBean
    private PublisherMapper publisherMapper;

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
    @DisplayName("Guest: Ok when getting books.")
    void anonymousUser_whenGettingBooks_thenOk() throws Exception {
        mockMvc.perform(get("/api/v1/book"))
                .andExpect(status().isOk());

        verify(bookService).findAll();
        verifyNoMoreInteractions(bookService);
    }

    @Test
    @DisplayName("Guest: Ok when getting a book.")
    void anonymousUser_whenGettingABook_thenOk() throws Exception {
        BookDTO dto = new BookDTO(
                1L, "Book title", null, 0, null,
                null, null, new AuthorSummaryDTO(1L, null),
                new PublisherSummaryDTO(1L, null)
        );

        when(bookService.findById(1)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/book/1"))
                .andExpect(status().isOk());

        verify(bookService).findById(1);
        verifyNoMoreInteractions(bookService);
    }

    @Test
    @DisplayName("Guest: Forbbiden when creating books.")
    void anonymousUser_whenCreatingABook_thenForbidden() throws Exception {
        mockMvc.perform(post("/api/v1/book/**"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Guest: Forbbiden when updating books.")
    void anonymousUser_whenUpdatingABook_thenForbidden() throws Exception {
        mockMvc.perform(put("/api/v1/book/**"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Guest: Forbbiden when deleting books.")
    void anonymousUser_whenDeletingABook_thenForbbiden() throws Exception {
        mockMvc.perform(delete("/api/v1/book/**"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("User: Ok when getting books.")
    @WithMockUser(username = "test", roles = "USER")
    void regularUser_whenGettingBooks_thenOk() throws Exception {
        mockMvc.perform(get("/api/v1/book/"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", roles = "USER")
    @DisplayName("User: Ok when getting a book.")
    void regularUser_whenGettingABook_thenOk() throws Exception {
        BookDTO dto = new BookDTO(
                1L, "Book title", null, 0, null,
                null, null, new AuthorSummaryDTO(1L, null),
                new PublisherSummaryDTO(1L, null)
        );

        when(bookService.findById(1)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/book/1"))
                .andExpect(status().isOk());

        verify(bookService).findById(1);
        verifyNoMoreInteractions(bookService);
    }

    @Test
    @WithMockUser(username = "test", roles = "USER")
    @DisplayName("User: Forbbiden when creating a book.")
    void regularUser_whenCreatingABook_thenForbidden() throws Exception {
        mockMvc.perform(post("/api/v1/book/**"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "test", roles = "USER")
    @DisplayName("User: Forbbiden when updating a book.")
    void regularUser_whenUpdatingABook_thenForbidden() throws Exception {
        mockMvc.perform(put("/api/v1/book/**"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "test", roles = "USER")
    @DisplayName("User: Forbbiden when deleting a book.")
    void regularUser_whenDeletingABook_thenForbbiden() throws Exception {
        mockMvc.perform(delete("/api/v1/book/**"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    @DisplayName("Admin: Ok when getting books.")
    void adminUser_whenGettingBooks_thenOk() throws Exception {
        mockMvc.perform(get("/api/v1/book"))
                .andExpect(status().isOk());

        verify(bookService).findAll();
        verifyNoMoreInteractions(bookService);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    @DisplayName("Admin: Ok when getting a book.")
    void adminUser_whenGettingABook_thenOk() throws Exception {
        BookDTO dto = new BookDTO(
                1L, "Book title", null, 0, null,
                null, null, new AuthorSummaryDTO(1L, null),
                new PublisherSummaryDTO(1L, null)
        );

        when(bookService.findById(1)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/book/1"))
                .andExpect(status().isOk());

        verify(bookService).findById(1);
        verifyNoMoreInteractions(bookService);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    @DisplayName("Admin: Ok when creating books.")
    void adminUser_whenCreatingABook_thenCreated() throws Exception {
        BookDTO dto = new BookDTO(
                1L, "Mistborn", null, 0, null, null,
                null, new AuthorSummaryDTO(1L, "Brandon Sanderson"),
                new PublisherSummaryDTO(1L, "Planet")
        );

        String body = new ObjectMapper().writeValueAsString(dto);

        Book entity = new Book();
        entity.setId(1);
        entity.setTitle("Mistborn");

        when(bookMapper.toEntity(any(BookDTO.class))).thenReturn(entity);
        when(bookService.save(any(Book.class))).thenReturn(entity);
        when(bookMapper.toDTO(any(Book.class))).thenReturn(dto);

        mockMvc.perform(post("/api/v1/book/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Mistborn"));

        verify(bookService).save(any(Book.class));
        verifyNoMoreInteractions(bookService);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    @DisplayName("Admin: Ok when updating books.")
    void adminUser_whenUpdatingABook_thenCreated() throws Exception {
        BookDTO dto = new BookDTO(
                1L, "Mistborn Updated", null, 0, null, null,
                null, new AuthorSummaryDTO(1L, "Brandon Sanderson"),
                new PublisherSummaryDTO(1L, "Planet")
        );

        String body = new ObjectMapper().writeValueAsString(dto);

        Book entity = new Book();
        entity.setId(1);
        entity.setTitle("Mistborn Updated");

        when(bookService.update(eq(1), any(Book.class))).thenReturn(dto);

        mockMvc.perform(put("/api/v1/book/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Mistborn Updated"));

        verify(bookService).update(eq(1), any(Book.class));
        verifyNoMoreInteractions(bookService);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    @DisplayName("Admin: NoContent when deleting books.")
    void adminUser_whenDeleteABook_thenNoContent() throws Exception {
        BookDTO dto = new BookDTO(
                1L, "Mistborn", null, 0, null, null,
                null, new AuthorSummaryDTO(1L, "Brandon Sanderson"),
                new PublisherSummaryDTO(1L, "Planet")
        );

        when(bookService.findById(1)).thenReturn(dto);
        when(bookMapper.toEntity(any(BookDTO.class))).thenReturn(new Book());

        mockMvc.perform(delete("/api/v1/book/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(bookService).findById(1);
        verify(bookService).delete(any(Book.class));
        verifyNoMoreInteractions(bookService);
    }
}
