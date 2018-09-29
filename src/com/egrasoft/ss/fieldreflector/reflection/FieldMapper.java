package com.egrasoft.ss.fieldreflector.reflection;

import java.lang.reflect.Field;

public abstract class FieldMapper<T> {
    private Class<T> fieldType;

    protected FieldMapper(Class<T> fieldType) {
        this.fieldType = fieldType;
    }

    public final T get(Object target, String fieldName) throws NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException {
        Field field = target.getClass().getField(fieldName);
        validateFieldType(field);

        return fieldType.cast(field.get(target));
    }

    public final void set(Object target, String fieldName, String rawValue) throws NoSuchFieldException,
            IllegalArgumentException, IllegalAccessException {
        Field field = target.getClass().getField(fieldName);
        validateFieldType(field);

        T value = convertRaw(rawValue);
        field.set(target, value);
    }

    public Class<T> getFieldType() {
        return fieldType;
    }

    protected abstract T convertRaw(String rawValue) throws IllegalArgumentException;

    private void validateFieldType(Field field) throws IllegalArgumentException {
        if (field.getType() != fieldType)
            throw new IllegalArgumentException("Wrong field type detected");
    }
}
