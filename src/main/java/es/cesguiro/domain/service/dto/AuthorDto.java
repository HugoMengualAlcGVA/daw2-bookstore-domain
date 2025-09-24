package es.cesguiro.domain.service.dto;

public record AuthorDto(
        String name,
        String nationality,
        String biographyEs,
        String biographyEn,
        int birthYear,
        Integer deathYear,
        String slug
) {
}
