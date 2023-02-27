package com.javamentor.qa.platform.service.abstracts.dto;

import java.util.Optional;

public interface ReadOnlyDtoService<E, K> {
    Optional<E> getById(K id);

}
