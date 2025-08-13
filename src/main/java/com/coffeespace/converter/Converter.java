package com.coffeespace.converter;

public interface Converter<M, E> {
    E modelToEntity(M model);
    M entityToModel(E entity);
}
