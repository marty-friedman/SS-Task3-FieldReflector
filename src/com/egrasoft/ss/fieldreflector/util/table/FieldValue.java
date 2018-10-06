package com.egrasoft.ss.fieldreflector.util.table;

import com.egrasoft.ss.fieldreflector.util.Constants;
import javafx.util.StringConverter;

public class FieldValue<T> {
    private T object;
    private Class<T> objClass;
    private boolean editable = false;

    public FieldValue(T object, Class<T> objClass) {
        this.object = object;
        this.objClass = objClass;
        if (Constants.Config.CONVERTERS.containsKey(objClass))
            this.editable = true;
    }

    @SuppressWarnings("unchecked")
    public FieldValue(T object) {
        if (object == null)
            throw new NullPointerException();
        this.object = object;
        this.objClass = (Class<T>) object.getClass();
        if (Constants.Config.CONVERTERS.containsKey(objClass))
            this.editable = true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public String toString() {
        StringConverter converter = Constants.Config.CONVERTERS.get(objClass);
        if (converter != null)
            return converter.toString(object);
        return String.valueOf(object);
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public Class<?> getObjClass() {
        return objClass;
    }

    public Object getObject() {
        return object;
    }

    @SuppressWarnings("unchecked")
    public static <T> FieldValue convertRaw(String rawValue, Class<T> objClass) {
        StringConverter converter = Constants.Config.CONVERTERS.get(objClass);
        if (converter == null)
            throw new IllegalStateException();
        return new FieldValue(converter.fromString(rawValue), objClass);
    }
}
