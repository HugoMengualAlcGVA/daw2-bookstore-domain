package es.cesguiro.domain.model;

import es.cesguiro.domain.exception.BusinessException;
import es.cesguiro.domain.model.Author;
import es.cesguiro.domain.model.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @ParameterizedTest(name = "{index} => basePrice={0}, discountPercentage={1}, expectedPrice={2}")
    @DisplayName("Calculate final price with various discounts")
    @CsvSource({
            "100.00, 15.0, 85.00",
            "50.00, 0.0, 50.00",
            "75.00, 100.0, 0.00",
            "60.00, -10.0, 60.00"
    })
    void calculateFinalPrice(String basePrice, double discountPercentage, String expectedPrice) {
        Book book = new Book(
                "978-3-16-148410-0",
                "Título en Español",
                "Title in English",
                "Sinopsis en Español",
                "Synopsis in English",
                new BigDecimal(basePrice),
                discountPercentage,
                "cover.jpg",
                LocalDate.of(2023, 1, 1),
                null,
                null
        );
        BigDecimal expected = new BigDecimal(expectedPrice).setScale(2, java.math.RoundingMode.HALF_UP);
        assertEquals(expected, book.getPrice());
    }

    @Test
    @DisplayName("Test add Author to Book")
    void testAddAuthorToBook() {
        ArrayList<Author> authors = new ArrayList<>();
        Author existingAuthor = new Author("Existing Author", null, null, null, 1900, null, null);
        authors.add(existingAuthor);
        Book book = new Book(
                "978-3-16-148410-0",
                "Título en Español",
                "Title in English",
                "Sinopsis en Español",
                "Synopsis in English",
                new BigDecimal("50.00"),
                10.0,
                "cover.jpg",
                LocalDate.of(2023, 1, 1),
                null,
                authors
        );
        Author author = new Author(
                "Author Name",
                "nationality",
                "BioEs",
                "BioEn",
                1980,
                null,
                "slug");
        book.addAuthor(author);
        assertTrue(book.getAuthors().contains(author), "Book should contain the added author");
    }

    @Test
    @DisplayName("Add Author to Book with null Authors list")
    void addAuthorToBookWithNullAuthorsList() {
        Book book = new Book(
                "978-3-16-148410-0",
                "Título en Español",
                "Title in English",
                "Sinopsis en Español",
                "Synopsis in English",
                new BigDecimal("50.00"),
                10.0,
                "cover.jpg",
                LocalDate.of(2023, 1, 1),
                null,
                null
        );
        Author author = new Author(
                "Author Name",
                "nationality",
                "BioEs",
                "BioEn",
                1980,
                null,
                "slug");
        book.addAuthor(author);
        assertTrue(book.getAuthors().contains(author), "Book should contain the added author");
    }

    @Test
    @DisplayName("Add existing Author to Book")
    void addExistingAuthorToBook() {
        Author author = new Author(
                "Author Name",
                "nationality",
                "BioEs",
                "BioEn",
                1980,
                null,
                "slug");

        Book book = new Book(
                "978-3-16-148410-0",
                "Título en Español",
                "Title in English",
                "Sinopsis en Español",
                "Synopsis in English",
                new BigDecimal("50.00"),
                10.0,
                "cover.jpg",
                LocalDate.of(2023, 1, 1),
                null,
                List.of(author)
        );
        assertThrows(BusinessException.class, () -> book.addAuthor(author));
    }

}