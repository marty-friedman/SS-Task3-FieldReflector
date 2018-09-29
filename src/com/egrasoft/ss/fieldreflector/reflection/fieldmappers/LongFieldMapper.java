package com.egrasoft.ss.fieldreflector.reflection.fieldmappers;

import com.egrasoft.ss.fieldreflector.reflection.FieldMapper;

public class LongFieldMapper extends FieldMapper<Long> {
    public LongFieldMapper() {
        super(Long.class);
    }

    @Override
    protected Long convertRaw(String rawValue) throws IllegalArgumentException {
        return Long.parseLong(rawValue);
    }
}
