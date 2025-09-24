package es.cesguiro.domain.service;

import es.cesguiro.domain.service.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<BookDto> getAll(int page, int size);

    BookDto getByIsbn(String isbn);

    Optional<BookDto> findByIsbn(String isbn);

    BookDto create(BookDto bookDto);

    BookDto update(BookDto bookDto);

    boolean delete(String isbn);

}
