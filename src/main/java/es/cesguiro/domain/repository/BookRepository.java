package es.cesguiro.domain.repository;

import es.cesguiro.domain.repository.entity.BookEntity;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    List<BookEntity> findAll(int page, int size);

    Optional<BookEntity> findByIsbn(String isbn);
}
