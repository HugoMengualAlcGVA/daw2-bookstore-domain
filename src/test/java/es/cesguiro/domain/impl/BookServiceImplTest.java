package es.cesguiro.domain.impl;

import es.cesguiro.domain.mapper.BookMapper;
import es.cesguiro.domain.model.Book;
import es.cesguiro.domain.repository.BookRepository;
import es.cesguiro.domain.repository.entity.AuthorEntity;
import es.cesguiro.domain.repository.entity.BookEntity;
import es.cesguiro.domain.repository.entity.PublisherEntity;
import es.cesguiro.domain.service.dto.BookDto;
import es.cesguiro.domain.service.impl.BookServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookServiceImpl;


    @Test
    @DisplayName("getAll should return list of books")
    void getAll_ShouldReturnListOfBooks() {
        // Arrange
        int page = 0;
        int size = 10;

        // Mock books
        BookEntity bookEntity1 = new BookEntity(
                "123",
                "TitleEs1",
                "TitleEn1",
                "SynopsisEs1",
                "SynopsisEn1",
                new BigDecimal("10.00"),
                5,
                "cover1.jpg", LocalDate.of(2020, 1, 1),
                null,
                null
        );
        BookEntity bookEntity2 = new BookEntity(
                "456",
                "TitleEs2",
                "TitleEn2",
                "SynopsisEs2",
                "SynopsisEn2",
                new BigDecimal("15.00"),
                10,
                "cover2.jpg", LocalDate.of(2021, 6, 15),
                null,
                null
        );
        List<BookEntity> bookEntities = List.of(bookEntity1, bookEntity2);
        when(bookRepository.findAll(page, size)).thenReturn(bookEntities);


        // Act
        List<BookDto> result = bookServiceImpl.getAll(page, size);

        // Assert
        assertAll(
                () -> assertNotNull(result, "Result should not be null"),
                () -> assertEquals(2, result.size(), "Result size should be 2"),
                () -> assertEquals("123", result.get(0).isbn(), "First book ISBN should match"),
                () -> assertEquals("456", result.get(1).isbn(), "Second book ISBN should match")
        );

        // Verify interaction with mock
        Mockito.verify(bookRepository).findAll(page, size);
    }

    void createBookMocks(int page, int size) {
        BookEntity bookEntity1 = new BookEntity(
                "123",
                "TitleEs1",
                "TitleEn1",
                "SynopsisEs1",
                "SynopsisEn1",
                new BigDecimal("10.00"),
                5,
                "cover1.jpg", LocalDate.of(2020, 1, 1),
                new PublisherEntity("alpaca","alpaca"),
                List.of(new AuthorEntity[]{
                            new AuthorEntity(
                                    "a",
                                    "s",
                                    "d",
                                    "r",
                                    1,
                                    2309,
                                    "d")
                })
        );
        BookEntity bookEntity2 = new BookEntity(
                "456",
                "TitleEs2",
                "TitleEn2",
                "SynopsisEs2",
                "SynopsisEn2",
                new BigDecimal("15.00"),
                10,
                "cover2.jpg", LocalDate.of(2021, 6, 15),
                new PublisherEntity("alpaca","alpaca"),
                List.of(new AuthorEntity[]{
                        new AuthorEntity(
                                "a",
                                "s",
                                "d",
                                "r",
                                1,
                                2309,
                                "d")
                })
        );
        List<BookEntity> bookEntities = List.of(bookEntity1, bookEntity2);
        when(bookRepository.findAll(page, size)).thenReturn(bookEntities);
    }

    // test getByIsbn when book exists
    @Test
    void getByIsbn() {
        // Arrange
        int page = 0;
        int size = 10;
        String isbn = "123";

        // Mock books
        createBookMocks(page, size);
        //Act
        BookDto result = bookServiceImpl.getByIsbn(isbn);

        // Assert
        assertAll(
                () -> assertNotNull(result, "Result should not be null"),
                () -> assertNotNull(result.titleEn(), "TitleEn should not be null"),
                () -> assertNotNull(result.titleEs(), "TitleEs should not be null"),
                () -> assertNotNull(result.synopsisEn(), "SynopsisEn should not be null"),
                () -> assertNotNull(result.synopsisEs(), "SynopsisEs should not be null"),
                () -> assertNotNull(result.basePrice(), "BasePrice should not be null"),
                () -> assertNotNull(result.cover(), "Cover should not be null"),
                () -> assertNotNull(result.publicationDate(), "PublicationDate should not be null"),
                () -> assertNotNull(result.authors(), "Authors should not be null"),
                () -> assertNotNull(result.publisher(), "Publisher should not be null"),
                () -> assertNotNull(result.authors(), "Authors should not be null")
        );

        // Verify interaction with mock
        Mockito.verify(bookRepository).findAll(page, size);
    }

    // test getByIsbn when book does not exist
    @Test
    void getByIsbnWhenNotInDB() {
        //Non-existent isbn
        String isbn = "789";
        // Arrange
        int page = 0;
        int size = 10;
        // Mock books
        createBookMocks(page, size);

        //Act
        BookDto result = bookServiceImpl.getByIsbn(isbn);

        // Assert
        assertAll(
                () -> assertNull(result, "Result should be Null")
        );

        // Verify interaction with mock
        Mockito.verify(bookRepository).findAll(page, size);
    }

    // test findByIsbn when book exists
    @Test
    void findByIsbn() {
        // Arrange
        int page = 0;
        int size = 10;
        String isbn = "123";

        //Mock books
        createBookMocks(page, size);

        //Act
        Optional<BookDto> result = bookServiceImpl.findByIsbn(isbn);

        // Assert
        result.ifPresent(bookDto -> assertAll(
                () -> assertNotNull(bookDto.titleEn(), "TitleEn should not be null"),
                () -> assertNotNull(bookDto.titleEs(), "TitleEs should not be null"),
                () -> assertNotNull(bookDto.synopsisEn(), "SynopsisEn should not be null"),
                () -> assertNotNull(bookDto.synopsisEs(), "SynopsisEs should not be null"),
                () -> assertNotNull(bookDto.basePrice(), "BasePrice should not be null"),
                () -> assertNotNull(bookDto.cover(), "Cover should not be null"),
                () -> assertNotNull(bookDto.publicationDate(), "PublicationDate should not be null"),
                () -> assertNotNull(bookDto.authors(), "Authors should not be null"),
                () -> assertNotNull(bookDto.publisher(), "Publisher should not be null"),
                () -> assertNotNull(bookDto.authors(), "Authors should not be null")
        ));

        // Verify interaction with mock
        Mockito.verify(bookRepository).findAll(page, size);
    }

    // test findByIsbn when book does not exist
    @Test
    void findByIsbnWhenNotInDB() {
        //Non-existent string
        String isbn = "6454";
        // Arrange
        int page = 0;
        int size = 10;

        // Mock books
        createBookMocks(page, size);

        //Act
        Optional<BookDto> result = bookServiceImpl.findByIsbn(isbn);

        // Assert
        assertAll(
                () -> assertTrue(result.isEmpty())
        );

        // Verify interaction with mock
        Mockito.verify(bookRepository).findAll(page, size);
    }

    // test create book
    @Test
    void createBook(){
        // Arrange
        int page = 0;
        int size = 10;

        // Mock books
        createBookMocks(page, size);

        //Act
        Book book = new Book(
                "1213",
                "TitleEs3",
                "TitleEn3",
                "SynopsisEs3",
                "SynopsisEn3",
                new BigDecimal("10.00"),
                5,
                "cover1.jpg", LocalDate.of(2020, 1, 1),
                null,
                null
        );
        BookDto result = bookServiceImpl.create(BookMapper.getInstance().fromBookToBookDto(book));

        //Assert
        assertAll(
                () -> assertNotNull(result)
        );

        // Verify interaction with mock
        Mockito.verify(bookRepository).findAll(page, size);
    }

    // test create book with existing isbn
    @Test
    void createBookWithExistingIsbn(){
        // Arrange
        int page = 0;
        int size = 10;

        // Mock books
        createBookMocks(page, size);

        //Act
        Book book = new Book(
                "1415",
                "TitleEs3",
                "TitleEn3",
                "SynopsisEs3",
                "SynopsisEn3",
                new BigDecimal("10.00"),
                5,
                "cover1.jpg", LocalDate.of(2020, 1, 1),
                null,
                null
        );
        BookDto result = bookServiceImpl.create(BookMapper.getInstance().fromBookToBookDto(book));

        //Assert
        assertAll(
                () -> assertNotNull(result)
        );

        // Verify interaction with mock
        Mockito.verify(bookRepository).findAll(page, size);
    }

    // test create book with invalid data
    @Test
    void createBookWithInvalidData(){
        // Arrange
        int page = 0;
        int size = 10;

        // Mock books
        createBookMocks(page, size);

        //Act
        Book bookPublisherMal = new Book(
                "1617",
                "TitleEs4",
                "TitleEn4",
                "SynopsisEs4",
                "SynopsisEn4",
                new BigDecimal("23.00"),
                5,
                "cover1.png",
                LocalDate.of(2020, 1, 1),
                null,
                null
        );
        Book bookIsbnMal = new Book(
                "albacete",
                "TitleEs4",
                "TitleEn4",
                "SynopsisEs4",
                "SynopsisEn4",
                new BigDecimal("23.00"),
                5,
                "cover1.png",
                LocalDate.of(2020, 1, 1),
                null,
                null
        );
        Book bookCoverMal = new Book(
                "212",
                "TitleEs4",
                "TitleEn4",
                "SynopsisEs5",
                "SynopsisEn5",
                new BigDecimal("23.00"),
                5,
                "sonido.mp3",
                LocalDate.of(2020, 1, 1),
                null,
                null
        );
        Book bookTitleMal = new Book(
                "213",
                "",
                "",
                "SynopsisEs6",
                "SynopsisEn6",
                new BigDecimal("23.00"),
                5,
                "sonido.mp3",
                LocalDate.of(2020, 1, 1),
                null,
                null
        );
        BookDto result1 = bookServiceImpl.create(BookMapper.getInstance().fromBookToBookDto(bookPublisherMal));
        BookDto result2 = bookServiceImpl.create(BookMapper.getInstance().fromBookToBookDto(bookIsbnMal));
        BookDto result3 = bookServiceImpl.create(BookMapper.getInstance().fromBookToBookDto(bookCoverMal));

        //Assert
        assertAll(
                () -> assertNull(result1),
                () -> assertNull(result2),
                () -> assertNull(result3)
        );

        // Verify interaction with mock
        Mockito.verify(bookRepository).findAll(page, size);
    }

    // test create book with non-existing authors

    // .....

    //test update book with invalid data
    @Test
    void createUpdateWithInvalidData(){
        // Arrange
        int page = 0;
        int size = 10;

        // Mock books
        createBookMocks(page, size);

        //Act
        Book bookPublisherMal = new Book(
                "1617",
                "TitleEs4",
                "TitleEn4",
                "SynopsisEs4",
                "SynopsisEn4",
                new BigDecimal("23.00"),
                5,
                "cover1.png",
                LocalDate.of(2020, 1, 1),
                null,
                null
        );
        Book bookIsbnMal = new Book(
                "albacete",
                "TitleEs4",
                "TitleEn4",
                "SynopsisEs4",
                "SynopsisEn4",
                new BigDecimal("23.00"),
                5,
                "cover1.png",
                LocalDate.of(2020, 1, 1),
                null,
                null
        );
        Book bookCoverMal = new Book(
                "212",
                "TitleEs4",
                "TitleEn4",
                "SynopsisEs5",
                "SynopsisEn5",
                new BigDecimal("23.00"),
                5,
                "sonido.mp3",
                LocalDate.of(2020, 1, 1),
                null,
                null
        );
        Book bookTitleMal = new Book(
                "213",
                "",
                "",
                "SynopsisEs6",
                "SynopsisEn6",
                new BigDecimal("23.00"),
                5,
                "sonido.mp3",
                LocalDate.of(2020, 1, 1),
                null,
                null
        );
        BookDto result1 = bookServiceImpl.update(BookMapper.getInstance().fromBookToBookDto(bookPublisherMal));
        BookDto result2 = bookServiceImpl.update(BookMapper.getInstance().fromBookToBookDto(bookIsbnMal));
        BookDto result3 = bookServiceImpl.update(BookMapper.getInstance().fromBookToBookDto(bookCoverMal));

        //Assert
        assertAll(
                () -> assertNull(result1),
                () -> assertNull(result2),
                () -> assertNull(result3)
        );

        // Verify interaction with mock
        Mockito.verify(bookRepository).findAll(page, size);
    }

    //test delete book when book exists
    @Test
    void deleteByIsbn() {
        // Arrange
        int page = 0;
        int size = 10;
        String isbn = "123";

        //Mock books
        createBookMocks(page, size);

        //Act
        boolean result = bookServiceImpl.delete(isbn);

        // Assert
        assertTrue(result);

        // Verify interaction with mock
        Mockito.verify(bookRepository).findAll(page, size);
    }

    //test delete book when book doesn't exist
    @Test
    void deleteByIsbnWhenIsNotInDB() {
        // Arrange
        int page = 0;
        int size = 10;
        String  isbn = "123";

        //Mock books
        createBookMocks(page, size);

        //Act
        boolean result = bookServiceImpl.delete(isbn);

        // Assert
        assertTrue(result);

        // Verify interaction with mock
        Mockito.verify(bookRepository).findAll(page, size);
    }

    //test delete book with invalid isbn

}