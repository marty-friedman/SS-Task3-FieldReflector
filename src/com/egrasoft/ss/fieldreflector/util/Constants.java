package com.egrasoft.ss.fieldreflector.util;

import javafx.util.StringConverter;
import javafx.util.converter.*;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Stream;

import static com.egrasoft.ss.fieldreflector.util.EntryHelper.entriesToMap;
import static com.egrasoft.ss.fieldreflector.util.EntryHelper.entry;

public class Constants {
    public static class Frame {
        public static final String MAIN_FRAME_TITLE_KEY = "frame.main.title";
    }

    public static class Dialogs {
        public static final String ABOUT_TITLE_KEY = "dialog.about.title";
        public static final String ABOUT_CONTENT_TEXT_KEY = "dialog.about.contenttext";
        public static final String ADD_CLASS_TITLE_KEY = "dialog.addclass.title";
        public static final String ADD_CLASS_CONTENT_TEXT_KEY = "dialog.addclass.contenttext";
        public static final String ERROR_TITLE_KEY = "dialog.error.title";
        public static final String ERROR_ILLEGAL_ACCESS_CONTENT_TEXT_KEY = "dialog.error.illegalaccessexc.text";
        public static final String ERROR_CLASS_NOT_FOUND_CONTENT_TEXT_KEY = "dialog.error.classnotfoundexc.text";
        public static final String ERROR_INSTANTIATION_CONTENT_TEXT_KEY = "dialog.error.instantiationexc.text";
        public static final String ERROR_CONVERSION_EXCEPTION_TEXT_KEY = "dialog.error.conversion.text";
        public static final String LANGUAGE_CHANGE_TITLE_KEY = "dialog.languagechange.title";
        public static final String LANGUAGE_CHANGE_TEXT_KEY = "dialog.languagechange.text";
        public static final String OK_TEXT_KEY = "dialog.button.ok";
        public static final String CANCEL_TEXT_KEY = "dialog.button.cancel";
    }

    public static class Location {
        public static final String MAIN_FRAME_VIEW_LOCATION = "fieldreflector/view/Main.fxml";
        public static final String CLASS_LIST_VIEW_LOCATION = "fieldreflector/view/ClassListView.fxml";
        public static final String FIELD_TABLE_VIEW_LOCATION = "fieldreflector/view/FieldTableView.fxml";
        public static final String CURRENCY_DATE_LABEL_LOCATION = "fieldreflector/view/CurrencyDateLabel.fxml";
    }

    public static class Table {
        public static final String VALUE_NOT_ACCESSIBLE = "table.cell.notaccessible";
    }

    public static class Localization {
        public static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
    }

    public static class Config {
        public static final Map<Class<?>, StringConverter<?>> CONVERTERS = Collections.unmodifiableMap(
                Stream.<Map.Entry<Class<?>, StringConverter<?>>>of(
                        entry(Boolean.class, new BooleanStringConverter()),
                        entry(Long.class, new LongStringConverter()),
                        entry(Integer.class, new IntegerStringConverter()),
                        entry(Float.class, new FloatStringConverter()),
                        entry(Double.class, new DoubleStringConverter()),
                        entry(String.class, new DefaultStringConverter()),
                        entry(boolean.class, new BooleanStringConverter()),
                        entry(long.class, new LongStringConverter()),
                        entry(int.class, new IntegerStringConverter()),
                        entry(float.class, new FloatStringConverter()),
                        entry(double.class, new DoubleStringConverter())
                ).collect(entriesToMap()));
    }
}
