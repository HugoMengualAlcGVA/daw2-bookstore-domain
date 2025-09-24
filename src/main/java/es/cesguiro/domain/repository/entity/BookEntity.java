package es.cesguiro.domain.repository.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
        Optional<PublisherEntity> publisher,
        Optional<List<AuthorEntity>> authors
) {
}
