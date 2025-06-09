package com.hbs.managamentservice.resolver;

public interface EntityResolver<T> {

    T resolveById(Long id);
}
