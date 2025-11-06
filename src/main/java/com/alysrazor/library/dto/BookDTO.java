package com.alysrazor.library.dto;

import java.time.LocalDate;

public record BookDTO(
        Long id,
        String title,
        String isbn,
        int pages,
        String language,
        String genre,
        LocalDate publication_date,
        AuthorSummaryDTO author,
        PublisherSummaryDTO publisher
) {
}
