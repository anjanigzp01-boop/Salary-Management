package com.acme.salarymanagement.api;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

/** Stable, framework-independent page contract used by the public API. */
public record PageResponse<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages) {

    public static <S, T> PageResponse<T> from(Page<S> page, Function<S, T> mapper) {
        return new PageResponse<>(page.getContent().stream().map(mapper).toList(), page.getNumber(),
                page.getSize(), page.getTotalElements(), page.getTotalPages());
    }
}
