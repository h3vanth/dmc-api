package io.bbw.dmc.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String id, Class<?> clazz) {
        super(new StringBuilder().append(clazz.getSimpleName()).append(" with given id ").append(id)
                .append(" doesn't exist.")
                .toString());
    }
}
