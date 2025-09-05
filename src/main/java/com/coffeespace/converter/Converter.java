package com.coffeespace.converter;

import java.util.List;
import java.util.stream.Collectors;

public interface Converter<M, E> {
    E modelToEntity(M model);
    M entityToModel(E entity);

    // Default method to convert list of models to list of entities
    default List<E> modelsToEntities(List<M> models) {
        return models == null ? List.of() :
                models.stream().map(this::modelToEntity).collect(Collectors.toList());
    }

    // Default method to convert list of entities to list of models
    default List<M> entitiesToModels(List<E> entities) {
        return entities == null ? List.of() :
                entities.stream().map(this::entityToModel).collect(Collectors.toList());
    }

    // Optional modify method (if needed for partial updates)
    default void modifyEntity(E entity, M model) {
        // Implement in specific converters if partial updates are required
    }
}
