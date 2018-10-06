package com.egrasoft.ss.fieldreflector.util.table;

import javafx.util.StringConverter;

public class FieldValueStringConverter extends StringConverter<FieldValue<?>> {
    @Override
    public String toString(FieldValue object) {
        return String.valueOf(object);
    }

    @Override
    public FieldValue fromString(String string) {
        return new RawFieldValue(string);
    }
}
