package es.cesguiro.domain.repository.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record BookEntity(
        String isbn,
        String titleEs,
        String titleEn,
        String synopsisEs,
        String synopsisEn,
        BigDecimal basePrice,
        double discountPercentage,
        String cover,
        LocalDate publicationDate,
        PublisherEntity publisher,
        List<AuthorEntity> authors
) {
}
