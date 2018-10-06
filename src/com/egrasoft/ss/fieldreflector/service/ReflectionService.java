package com.egrasoft.ss.fieldreflector.service;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReflectionService {
    private ReflectionService() {
    }

    public Object instantiateClass(Class<?> objClass) throws IllegalAccessException, InstantiationException {
        return objClass.newInstance();
    }

    public List<Field> getFields(Object instance) {
        return Arrays.stream(instance.getClass().getDeclaredFields())
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .peek(field -> field.setAccessible(true))
                .collect(Collectors.toList());
    }

    public Object getFieldValue(Object source, Field field) throws IllegalAccessException {
        return field.get(source);
    }

    public void setFieldValue(Object target, Field field, Object value) throws IllegalAccessException {
        field.set(target, value);
    }

    public static ReflectionService getInstance() {
        return SingletonHelper.instance;
    }

    private static class SingletonHelper {
        private static final ReflectionService instance = new ReflectionService();
    }
}
