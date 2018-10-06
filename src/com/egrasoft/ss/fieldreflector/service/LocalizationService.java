package com.egrasoft.ss.fieldreflector.service;

import com.egrasoft.ss.fieldreflector.util.Language;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationService {
    private static final String RESOURCE_BUNDLE_LOCATION = "fieldreflector/localization/locales";

    private Language currentLanguage = Language.RUSSIAN;

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
