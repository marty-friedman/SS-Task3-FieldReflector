package com.egrasoft.ss.fieldreflector.service;

import com.egrasoft.ss.fieldreflector.util.Constants;
import com.egrasoft.ss.fieldreflector.util.Language;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Currency;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationService {
    private static final String RESOURCE_BUNDLE_LOCATION = "fieldreflector/localization/locales";

    private Language currentLanguage = Constants.Localization.DEFAULT_LANGUAGE;

    public String getString(String key) {
        return getCurrentBundle().getString(key);
    }

    public String getString(String key, Locale locale) {
        return ResourceBundle.getBundle(RESOURCE_BUNDLE_LOCATION, locale).getString(key);
    }

    public ResourceBundle getCurrentBundle() {
        return ResourceBundle.getBundle(RESOURCE_BUNDLE_LOCATION, currentLanguage.getLocale());
    }

    public Language getCurrentLanguage() {
        return currentLanguage;
    }

    public Currency getDefaultCurrency() {
        return Currency.getInstance(currentLanguage.getLocale());
    }

    public DateTimeFormatter getDateTimeFormatter() {
        return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL).withLocale(currentLanguage.getLocale());
    }

    public void setCurrentLanguage(Language currentLanguage) {
        this.currentLanguage = currentLanguage;
    }

    public static LocalizationService getInstance() {
        return SingletonHelper.instance;
    }

    private LocalizationService() {}

    private static class SingletonHelper {
        private static final LocalizationService instance = new LocalizationService();
    }
}
