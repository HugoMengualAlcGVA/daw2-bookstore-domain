package es.cesguiro.domain.repository;

import es.cesguiro.domain.repository.entity.AuthorEntity;

public interface AuthorRepository {
    AuthorEntity create(AuthorEntity authorEntity);
}
