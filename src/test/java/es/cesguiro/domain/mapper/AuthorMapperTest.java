package es.cesguiro.domain.mapper;

import es.cesguiro.domain.exception.BusinessException;
import es.cesguiro.domain.mapper.AuthorMapper;
import es.cesguiro.domain.repository.entity.AuthorEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorMapperTest {

    @Test
    @DisplayName("Test map AuthorEntity to Author")
    void toAuthor() {
        // Arrange
        AuthorEntity authorEntity = new AuthorEntity("name", "nationality", "biographyEs", "biographyEn", 1990, 2020, "slug");

        // Act
        var author = AuthorMapper.getInstance().fromAuthorEntityToAuthor(authorEntity);

        // Assert
        assertAll(
                () -> assertEquals(authorEntity.name(), author.getName(), "Names should match"),
                () -> assertEquals(authorEntity.nationality(), author.getNationality(), "Nationalities should match"),
                () -> assertEquals(authorEntity.biographyEs(), author.getBiographyEs(), "BiographyEs should match"),
                () -> assertEquals(authorEntity.biographyEn(), author.getBiographyEn(), "BiographyEn should match"),
                () -> assertEquals(authorEntity.birthYear(), author.getBirthYear(), "Birth years should match"),
                () -> assertEquals(authorEntity.deathYear(), author.getDeathYear(), "Death years should match"),
                () -> assertEquals(authorEntity.slug(), author.getSlug(), "Slugs should match")
        );
    }

    @Test
    @DisplayName("Test map null AuthorEntity to Author throws BusinessException")
    void toAuthor_NullAuthorEntity_ThrowsBusinessException() {
        // Arrange
        AuthorEntity authorEntity = null;
        // Act & Assert
        var exception = assertThrows(BusinessException.class, () -> AuthorMapper.getInstance().fromAuthorEntityToAuthor(authorEntity));
        assertEquals("AuthorEntity cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Test map List<AuthorEntity> to Author")
    void toAuthorList() {
        // Arrange
        var authorEntities = java.util.List.of(
                new AuthorEntity("name1", "nationality1", "biographyEs1", "biographyEn1", 1990, 2020, "slug1"),
                new AuthorEntity("name2", "nationality2", "biographyEs2", "biographyEn2", 1980, null, "slug2")
        );

        // Act
        var authors = authorEntities.stream().map(AuthorMapper.getInstance()::fromAuthorEntityToAuthor).toList();

        // Assert
        assertAll(
                () -> assertEquals(2, authors.size(), "There should be two authors"),
                () -> {
                    var author1 = authors.get(0);
                    var entity1 = authorEntities.get(0);
                    assertAll(
                            () -> assertEquals(entity1.name(), author1.getName(), "Names should match"),
                            () -> assertEquals(entity1.nationality(), author1.getNationality(), "Nationalities should match"),
                            () -> assertEquals(entity1.biographyEs(), author1.getBiographyEs(), "BiographyEs should match"),
                            () -> assertEquals(entity1.biographyEn(), author1.getBiographyEn(), "BiographyEn should match"),
                            () -> assertEquals(entity1.birthYear(), author1.getBirthYear(), "Birth years should match"),
                            () -> assertEquals(entity1.deathYear(), author1.getDeathYear(), "Death years should match"),
                            () -> assertEquals(entity1.slug(), author1.getSlug(), "Slugs should match")
                    );
                },
                () -> {
                    var author2 = authors.get(1);
                    var entity2 = authorEntities.get(1);
                    assertAll(
                            () -> assertEquals(entity2.name(), author2.getName(), "Names should match"),
                            () -> assertEquals(entity2.nationality(), author2.getNationality(), "Nationalities should match"),
                            () -> assertEquals(entity2.biographyEs(), author2.getBiographyEs(), "BiographyEs should match"),
                            () -> assertEquals(entity2.biographyEn(), author2.getBiographyEn(), "BiographyEn should match"),
                            () -> assertEquals(entity2.birthYear(), author2.getBirthYear(), "Birth years should match"),
                            () -> assertEquals(entity2.deathYear(), author2.getDeathYear(), "Death years should match"),
                            () -> assertEquals(entity2.slug(), author2.getSlug(), "Slugs should match")
                    );
                }
        );
    }

}