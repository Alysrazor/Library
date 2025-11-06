package com.alysrazor.library.dto;

import java.time.LocalDate;
import java.util.List;

public record AuthorDTO(
        Long id,
        String name,
        LocalDate birthDate,
        String nationality,
        String email,
        String website,
        List<BookDTO> bookList
) { }
