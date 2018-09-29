package com.egrasoft.ss.fieldreflector.reflection.fieldmappers;

import com.egrasoft.ss.fieldreflector.reflection.FieldMapper;

public class DoubleFieldMapper extends FieldMapper<Double> {
    public DoubleFieldMapper() {
        super(Double.class);
    }

    @Override
    protected Double convertRaw(String rawValue) throws IllegalArgumentException {
        return Double.parseDouble(rawValue);
    }
}
