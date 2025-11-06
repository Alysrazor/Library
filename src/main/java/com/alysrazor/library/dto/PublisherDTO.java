package com.alysrazor.library.dto;

import java.util.List;
import java.util.Objects;

public record PublisherDTO(
        Long id,
        String name,
        String country,
        String website,
        String email,
        String address,
        List<BookDTO> bookList
) { }
