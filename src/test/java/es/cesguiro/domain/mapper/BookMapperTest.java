package es.cesguiro.domain.mapper;

import es.cesguiro.domain.exception.BusinessException;
import es.cesguiro.domain.mapper.BookMapper;
import es.cesguiro.domain.model.Author;
import es.cesguiro.domain.model.Book;
import es.cesguiro.domain.model.Publisher;
import es.cesguiro.domain.repository.entity.AuthorEntity;
import es.cesguiro.domain.repository.entity.BookEntity;
import es.cesguiro.domain.repository.entity.PublisherEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookMapperTest {

    @Test
    @DisplayName("Test map Book to BookDto")
    void toBookDto() {
        // Arrange
        var book = new Book(
                "978-84-376-0494-7",
                "TitleEs",
                "TitleEn",
                "SynopsisEs",
                "SynopsisEn",
                new BigDecimal("20.00"),
                10,
                "cover.jpg",
                LocalDate.of(2020, 1, 1),
                null,
                null
        );
        book.setPublisher(new Publisher("Publisher Name", "publisher-slug"));
        book.setAuthors(List.of(
                new Author("Author One", "nationality1", "Bio1", "Bio1En", 1970, null, "author-one"),
                new Author("Author Two", "nationality2", "Bio2", "Bio2En", 1980, 2020, "author-two")
        ));

        // Act
        var bookDto = BookMapper.getInstance().fromBookToBookDto(book);

        // Assert
        assertAll(
                () -> assertEquals(book.getIsbn(), bookDto.isbn(), "ISBN should match"),
                () -> assertEquals(book.getTitleEs(), bookDto.titleEs(), "TitleEs should match"),
                () -> assertEquals(book.getTitleEn(), bookDto.titleEn(), "TitleEn should match"),
                () -> assertEquals(book.getSynopsisEs(), bookDto.synopsisEs(), "SynopsisEs should match"),
                () -> assertEquals(book.getSynopsisEn(), bookDto.synopsisEn(), "SynopsisEn should match"),
                () -> assertEquals(book.getBasePrice(), bookDto.basePrice(), "BasePrice should match"),
                () -> assertEquals(book.getDiscountPercentage(), bookDto.discountPercentage(), "DiscountPercentage should match"),
                () -> assertEquals(book.getCover(), bookDto.cover(), "Cover should match"),
                () -> assertEquals(book.getPublicationDate(), bookDto.publicationDate(), "PublicationDate should match"),
                () -> assertNotNull(bookDto.publisher(), "Publisher should not be null"),
                () -> assertEquals(book.getPublisher().getName(), bookDto.publisher().name(), "Publisher name should match"),
                () -> assertEquals(book.getPublisher().getSlug(), bookDto.publisher().slug(), "Publisher slug should match"),
                () -> assertNotNull(bookDto.authors(), "Authors should not be null"),
                () -> assertEquals(2, bookDto.authors().size(), "Authors size should match"),
                () -> assertEquals("Author One", bookDto.authors().get(0).name(), "First author name should match"),
                () -> assertEquals("Author Two", bookDto.authors().get(1).name(), "Second author name should match")
        );
    }

    @Test
    @DisplayName("Test map null Book to BookDto throws BusinessException")
    void toBookDto_NullBook_ThrowsBusinessException() {
        // Arrange
        Book book = null;
        // Act & Assert
        assertThrows(BusinessException.class, () -> BookMapper.getInstance().fromBookToBookDto(book));
    }

    @Test
    @DisplayName("Test map BookEntity to Book")
    void toBook() {
        // Arrange
        var bookEntity = new BookEntity(
                "978-84-376-0494-7",
                "TitleEs",
                "TitleEn",
                "SynopsisEs",
                "SynopsisEn",
                new BigDecimal("20.00"),
                10,
                "cover.jpg",
                LocalDate.of(2020, 1, 1),
                new PublisherEntity("Publisher Name", "publisher-slug"),
                List.of(
                        new AuthorEntity("Author One", "nationality1", "Bio1", "Bio1En", 1970, null, "author-one"),
                        new AuthorEntity("Author Two", "nationality2", "Bio2", "Bio2En", 1980, 2020, "author-two")
                )
        );

        // Act
        var book = BookMapper.getInstance().fromBookEntityToBook(bookEntity);

        // Assert
        assertAll(
                () -> assertEquals(bookEntity.isbn(), book.getIsbn(), "ISBN should match"),
                () -> assertEquals(bookEntity.titleEs(), book.getTitleEs(), "TitleEs should match"),
                () -> assertEquals(bookEntity.titleEn(), book.getTitleEn(), "TitleEn should match"),
                () -> assertEquals(bookEntity.synopsisEs(), book.getSynopsisEs(), "SynopsisEs should match"),
                () -> assertEquals(bookEntity.synopsisEn(), book.getSynopsisEn(), "SynopsisEn should match"),
                () -> assertEquals(bookEntity.basePrice(), book.getBasePrice(), "BasePrice should match"),
                () -> assertEquals(new BigDecimal("18.00"), book.getPrice(), "FinalPrice should match"),
                () -> assertEquals(bookEntity.discountPercentage(), book.getDiscountPercentage(), "DiscountPercentage should match"),
                () -> assertEquals(bookEntity.cover(), book.getCover(), "Cover should match"),
                () -> assertEquals(bookEntity.publicationDate(), book.getPublicationDate(), "PublicationDate should match"),
                () -> assertNotNull(book.getPublisher(), "Publisher should not be null"),
                () -> assertEquals(bookEntity.publisher().name(), book.getPublisher().getName(), "Publisher name should match"),
                () -> assertEquals(bookEntity.publisher().slug(), book.getPublisher().getSlug(), "Publisher slug should match"),
                () -> assertNotNull(book.getAuthors(), "Authors should not be null"),
                () -> assertEquals(2, book.getAuthors().size(), "Authors size should match"),
                () -> assertEquals("Author One", book.getAuthors().get(0).getName(), "First author name should match"),
                () -> assertEquals("Author Two", book.getAuthors().get(1).getName(), "Second author name should match")
        );
    }

}