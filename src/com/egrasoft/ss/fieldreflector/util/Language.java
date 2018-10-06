package com.egrasoft.ss.fieldreflector.util;

import java.util.Locale;

public enum Language {
    ENGLISH(Locale.forLanguageTag("en-US"), "English"), RUSSIAN(Locale.forLanguageTag("ru-RU"), "Русский");

    private Locale locale;
    private String name;

    Language(Locale locale, String name) {
        this.locale = locale;
        this.name = name;
    }

    public Locale getLocale() {
        return locale;
    }

    @Override
    public String toString() {
        return name;
    }
}
