package es.cesguiro.domain.service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record BookDto(
        String isbn,
        String titleEs,
        String titleEn,
        String synopsisEs,
        String synopsisEn,
        BigDecimal basePrice,
        double discountPercentage,
        BigDecimal price,
        String cover,
        LocalDate publicationDate,
        PublisherDto publisher,
        List<AuthorDto> authors
) {
}
