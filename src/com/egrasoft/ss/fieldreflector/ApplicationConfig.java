package com.egrasoft.ss.fieldreflector;

import com.egrasoft.ss.fieldreflector.reflection.FieldMapper;
import com.egrasoft.ss.fieldreflector.reflection.fieldmappers.*;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ApplicationConfig {
    public static final Map<Class<?>, FieldMapper<?>> FIELD_MAPPERS = Collections.unmodifiableMap(Stream.of(
            new BooleanFieldMapper(),
            new DoubleFieldMapper(),
            new IntegerFieldMapper(),
            new LongFieldMapper(),
            new StringFieldMapper()
    ).collect(Collectors.toMap(FieldMapper::getFieldType, Function.identity())));
}
