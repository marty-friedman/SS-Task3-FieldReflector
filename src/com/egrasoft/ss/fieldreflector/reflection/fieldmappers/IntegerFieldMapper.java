package com.egrasoft.ss.fieldreflector.reflection.fieldmappers;

import com.egrasoft.ss.fieldreflector.reflection.FieldMapper;

public class IntegerFieldMapper extends FieldMapper<Integer> {
    public IntegerFieldMapper() {
        super(Integer.class);
    }

    @Override
    protected Integer convertRaw(String rawValue) throws IllegalArgumentException {
        return Integer.parseInt(rawValue);
    }
}
