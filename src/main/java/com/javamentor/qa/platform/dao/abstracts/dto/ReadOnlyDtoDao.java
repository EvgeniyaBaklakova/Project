package com.javamentor.qa.platform.dao.abstracts.dto;

import java.util.Optional;

public interface ReadOnlyDtoDao<E, K> {
    Optional<E> getById(K id);

}
