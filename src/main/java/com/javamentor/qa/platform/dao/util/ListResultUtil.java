package com.javamentor.qa.platform.dao.util;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class ListResultUtil {
    public static <T> Optional<List<T>> getListResultOrNull(TypedQuery<T> var) {
        try {
            return Optional.of(var.getResultList());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}