package com.egrasoft.ss.fieldreflector.reflection.fieldmappers;

import com.egrasoft.ss.fieldreflector.reflection.FieldMapper;

public class BooleanFieldMapper extends FieldMapper<Boolean> {
    public BooleanFieldMapper() {
        super(Boolean.class);
    }

    @Override
    protected Boolean convertRaw(String rawValue) throws IllegalArgumentException {
        return Boolean.parseBoolean(rawValue);
    }
}
