package com.egrasoft.ss.fieldreflector.reflection.fieldmappers;

import com.egrasoft.ss.fieldreflector.reflection.FieldMapper;

public class StringFieldMapper extends FieldMapper<String> {
    public StringFieldMapper() {
        super(String.class);
    }

    @Override
    protected String convertRaw(String rawValue) throws IllegalArgumentException {
        return rawValue;
    }
}
