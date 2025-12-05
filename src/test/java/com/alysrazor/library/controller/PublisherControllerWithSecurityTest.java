package com.alysrazor.library.controller;

import com.alysrazor.library.config.JwtAuthFilter;
import com.alysrazor.library.config.SecurityConfig;
import com.alysrazor.library.dto.BookDTO;
import com.alysrazor.library.dto.PublisherDTO;
import com.alysrazor.library.entity.Publisher;
import com.alysrazor.library.exception.PublisherHasBooksException;
import com.alysrazor.library.mapper.PublisherMapper;
import com.alysrazor.library.repository.TokenRepository;
import com.alysrazor.library.repository.UserRepository;
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

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PublisherController.class)
@Import({SecurityConfig.class, JwtAuthFilter.class})
public class PublisherControllerWithSecurityTest {
    @Autowired
    private MockMvc mockMvc;

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
    @DisplayName("Guest: Ok when getting publishers")
    void anonymousUser_whenGettingPublishers_thenOk() throws Exception {
        mockMvc.perform(get("/api/v1/publisher"))
                .andExpect(status().isOk());

        verify(publisherService).findAll();
        verifyNoMoreInteractions(publisherService);
    }

    @Test
    @DisplayName("Guest: Ok when getting a publisher")
    void anonymousUser_whenGettingAPublisher_thenOk() throws Exception {
        PublisherDTO dto = new PublisherDTO(
                1L, "Tor Books", null, null, null, null,
                null
        );

        when(publisherService.findById(1)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/publisher/1"))
                .andExpect(status().isOk());

        verify(publisherService).findById(1);
        verifyNoMoreInteractions(publisherService);
    }

    @Test
    @DisplayName("Guest: Forbbiden when creating publishers")
    void anonymousUser_whenCreatingAPublisher_thenForbidden() throws Exception {
        mockMvc.perform(post("/api/v1/publisher"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Guest: Forbbiden when updating publishers")
    void anonymousUser_whenUpdatingAPublisher_thenForbidden() throws Exception {
        mockMvc.perform(put("/api/v1/publisher"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Guest: Forbbiden when deleting publishers")
    void anonymousUser_whenDeletingAPublisher_thenForbidden() throws Exception {
        mockMvc.perform(delete("/api/v1/publisher"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "test", roles = "USER")
    @DisplayName("User: Ok when getting publishers")
    void regularUser_whenGettingPublishers_thenOk() throws Exception {
        mockMvc.perform(get("/api/v1/publisher"))
                .andExpect(status().isOk());

        verify(publisherService).findAll();
        verifyNoMoreInteractions(publisherService);
    }

    @Test
    @WithMockUser(username = "test", roles = "USER")
    @DisplayName("User: Ok when getting a publisher")
    void regularUser_whenGettingAPublisher_thenOk() throws Exception {
        PublisherDTO dto = new PublisherDTO(
                1L, "Tor Books", null, null, null, null,
                null
        );

        when(publisherService.findById(1)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/publisher/1"))
                .andExpect(status().isOk());

        verify(publisherService).findById(1);
        verifyNoMoreInteractions(publisherService);
    }

    @Test
    @WithMockUser(username = "test", roles = "USER")
    @DisplayName("User: Forbbiden when creating publishers")
    void regularUser_whenCreatingAPublisher_thenForbidden() throws Exception {
        mockMvc.perform(post("/api/v1/publisher"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "test", roles = "USER")
    @DisplayName("User: Forbbiden when updating publishers")
    void regularUser_whenUpdatingAPublisher_thenForbidden() throws Exception {
        mockMvc.perform(put("/api/v1/publisher"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "test", roles = "USER")
    @DisplayName("User: Forbbiden when creating publishers")
    void regularUser_whenDeletingAPublisher_thenForbidden() throws Exception {
        mockMvc.perform(delete("/api/v1/publisher"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    @DisplayName("Admin: Ok when getting publishers")
    void adminUser_whenGettingPublishers_thenOk() throws Exception {
        mockMvc.perform(get("/api/v1/publisher"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    @DisplayName("Admin: Ok when getting a publisher")
    void adminUser_whenGettingAPublisher_thenOk() throws Exception {
        PublisherDTO dto = new PublisherDTO(
                1L, "Tor Books", null, null, null, null,
                null
        );

        when(publisherService.findById(1)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/publisher/1"))
                .andExpect(status().isOk());

        verify(publisherService).findById(1);
        verifyNoMoreInteractions(publisherService);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    @DisplayName("Admin: Created when creating a publisher")
    void adminUser_whenCreatingAPublisher_thenCreated() throws Exception {
        PublisherDTO dto = new PublisherDTO(
                1L, "Tor Books", null, null, null,
                null, null
        );

        String body = new ObjectMapper().writeValueAsString(dto);

        Publisher entity = new Publisher();
        entity.setId(1);
        entity.setName("Tor Books");

        when(publisherMapper.toEntity(any(PublisherDTO.class))).thenReturn(entity);
        when(publisherService.save(any(Publisher.class))).thenReturn(entity);
        when(publisherMapper.toDTO(any(Publisher.class))).thenReturn(dto);

        mockMvc.perform(post("/api/v1/publisher")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Tor Books"));

        verify(publisherService).save(any(Publisher.class));
        verifyNoMoreInteractions(publisherService);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    @DisplayName("Admin: Created when updating a publisher")
    void adminUser_whenUpdatingAPublisher_thenCreated() throws Exception {
        PublisherDTO dto = new PublisherDTO(
                1L, "Tor Books Updated", null, null, null,
                null, null
        );

        String body = new ObjectMapper().writeValueAsString(dto);

        when(publisherService.update(eq(1), any(Publisher.class))).thenReturn(dto);

        mockMvc.perform(put("/api/v1/publisher/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Tor Books Updated"));

        verify(publisherService).update(eq(1), any(Publisher.class));
        verifyNoMoreInteractions(publisherService);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    @DisplayName("Admin: Exception when deleting a publisher that has books.")
    void adminUser_whenDeletingAPublisherHasBooks_thenException() throws Exception {
        PublisherDTO dto = new PublisherDTO(
                1L, "Tor Books", null, null, null,
                null, List.of(new BookDTO(
                        1L, "Book", null, 0, null, null,
                null, null, null
                ))
        );

        when(publisherService.findById(1)).thenReturn(dto);
        when(publisherMapper.toEntity(any(PublisherDTO.class))).thenReturn(new Publisher());
        doThrow(new PublisherHasBooksException(1)).when(publisherService).delete(any(Publisher.class));

        mockMvc.perform(delete("/api/v1/publisher/1")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.details").value("Can't delete publisher."));

        verify(publisherService).findById(1);
        verify(publisherService).delete(any(Publisher.class));
        verifyNoMoreInteractions(publisherService);
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    @DisplayName("Admin: NoContent when deleting a publisher.")
    void adminUser_whenDeletingAPublisherHasBooks_thenNoContent() throws Exception {
        PublisherDTO dto = new PublisherDTO(
                1L, "Tor Books", null, null, null,
                null, List.of());

        when(publisherService.findById(1)).thenReturn(dto);
        when(publisherMapper.toEntity(any(PublisherDTO.class))).thenReturn(new Publisher());

        mockMvc.perform(delete("/api/v1/publisher/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(publisherService).findById(1);
        verify(publisherService).delete(any(Publisher.class));
        verifyNoMoreInteractions(publisherService);
    }
}
