package com.egrasoft.ss.fieldreflector.controller;

import com.egrasoft.ss.fieldreflector.javafx.ClassListView;
import com.egrasoft.ss.fieldreflector.javafx.CurrencyDateLabel;
import com.egrasoft.ss.fieldreflector.javafx.FieldTableView;
import com.egrasoft.ss.fieldreflector.service.FrameService;
import com.egrasoft.ss.fieldreflector.service.LocalizationService;
import com.egrasoft.ss.fieldreflector.service.ReflectionService;
import com.egrasoft.ss.fieldreflector.util.Constants;
import com.egrasoft.ss.fieldreflector.util.Language;
import com.egrasoft.ss.fieldreflector.util.PersistenceHelper;
import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

import static com.egrasoft.fxcommons.util.ControllerUtils.createChoiceDialog;
import static com.egrasoft.fxcommons.util.ControllerUtils.createMessageDialog;
import static com.egrasoft.fxcommons.util.ControllerUtils.createTextInputDialog;

public class MainFrameController {
    private LocalizationService localizationService = LocalizationService.getInstance();
    private ReflectionService reflectionService = ReflectionService.getInstance();
    private FrameService frameService = FrameService.getInstance();

    @FXML
    private ClassListView classList;
    @FXML
    private FieldTableView fieldTable;
    @FXML
    private CurrencyDateLabel currencyDateLabel;

    private Stage stage;
    private ObservableList<Class<?>> classes = FXCollections.observableArrayList();
    private Map<Class<?>, Object> instanceMap = new HashMap<>();
    private Property<Object> instanceProperty = new SimpleObjectProperty<>();

    public MainFrameController(Stage stage) {
        this.stage = stage;
    }

    public MainFrameController(Stage stage, PersistenceHelper persistenceHelper) {
        this(stage);
        restoreFromPersistenceHelperBeforeInit(persistenceHelper);
    }

    @FXML
    @SuppressWarnings("unchecked")
    private void initialize() {
        classList.initialize(classes, instanceMap, instanceProperty);
        fieldTable.initialize(instanceProperty);
        currencyDateLabel.initialize();
    }

    @FXML
    private void doQuit() {
        Platform.exit();
    }

    @FXML
    private void doAbout() {
        createMessageDialog(Alert.AlertType.INFORMATION,
                localizationService.getString(Constants.Dialogs.ABOUT_TITLE_KEY),
                localizationService.getString(Constants.Dialogs.ABOUT_CONTENT_TEXT_KEY),
                localizationService.getString(Constants.Dialogs.OK_TEXT_KEY)).showAndWait();
    }

    @FXML
    private void doAddClass() {
        createTextInputDialog(localizationService.getString(Constants.Dialogs.ADD_CLASS_TITLE_KEY),
                localizationService.getString(Constants.Dialogs.ADD_CLASS_CONTENT_TEXT_KEY),
                "com.example.SampleBean",
                localizationService.getString(Constants.Dialogs.OK_TEXT_KEY),
                localizationService.getString(Constants.Dialogs.CANCEL_TEXT_KEY)).showAndWait()
                .map(name -> name.contains(".") ? name : "java.lang." + name)
                .ifPresent(this::tryAddClass);
    }

    @FXML
    private void doNewInstance() {
        Optional<Class<?>> selection = classList.getSelectionModel().getSelectedItems().stream().findAny();
        classList.getSelectionModel().clearSelection();
        selection.ifPresent(this::tryInstantiateClass);
    }

    @FXML
    private void doChangeLanguage() throws IOException {
        Optional<Language> language = createChoiceDialog(Arrays.asList(Language.values()), localizationService.getCurrentLanguage(),
                localizationService.getString(Constants.Dialogs.LANGUAGE_CHANGE_TITLE_KEY),
                localizationService.getString(Constants.Dialogs.LANGUAGE_CHANGE_TEXT_KEY),
                localizationService.getString(Constants.Dialogs.OK_TEXT_KEY),
                localizationService.getString(Constants.Dialogs.CANCEL_TEXT_KEY)).showAndWait();
        if (language.isPresent() && language.get() != localizationService.getCurrentLanguage()) {
            localizationService.setCurrentLanguage(language.get());
            frameService.reloadMainFrame(stage, storeToPersistenceHelper());
        }
    }

    private void tryAddClass(String className) {
        Class<?> objClass = null;
        try {
            objClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            createMessageDialog(Alert.AlertType.ERROR,
                    localizationService.getString(Constants.Dialogs.ERROR_TITLE_KEY),
                    localizationService.getString(Constants.Dialogs.ERROR_CLASS_NOT_FOUND_CONTENT_TEXT_KEY),
                    localizationService.getString(Constants.Dialogs.OK_TEXT_KEY)).showAndWait();
        }

        if (objClass != null)
            tryInstantiateClass(objClass);
    }

    private void tryInstantiateClass(Class<?> objClass) {
        String errorKey = null;
        try {
            Object instance = reflectionService.instantiateClass(objClass);

            instanceMap.put(objClass, instance);
            if (!classes.contains(objClass))
                classes.add(objClass);
            classList.getSelectionModel().select(objClass);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            errorKey = Constants.Dialogs.ERROR_ILLEGAL_ACCESS_CONTENT_TEXT_KEY;
        } catch (InstantiationException e) {
            e.printStackTrace();
            errorKey = Constants.Dialogs.ERROR_INSTANTIATION_CONTENT_TEXT_KEY;
        }

        if (errorKey != null)
            createMessageDialog(Alert.AlertType.ERROR,
                    localizationService.getString(Constants.Dialogs.ERROR_TITLE_KEY),
                    localizationService.getString(errorKey),
                    localizationService.getString(Constants.Dialogs.OK_TEXT_KEY)).showAndWait();
    }

    private void restoreFromPersistenceHelperBeforeInit(PersistenceHelper helper) {
        classes.addAll(helper.getClasses());
        instanceMap = helper.getInstanceMap();
    }

    private PersistenceHelper storeToPersistenceHelper() {
        PersistenceHelper helper = new PersistenceHelper();
        helper.setClasses(new ArrayList<>(classes));
        Object currentInstance = instanceProperty.getValue();
        helper.setCurrentClass(currentInstance != null ? currentInstance.getClass() : null);
        helper.setInstanceMap(instanceMap);
        return helper;
    }

    public void restoreFromPersistenceHelperAfterInit(PersistenceHelper helper) {
        Class<?> currentClass = helper.getCurrentClass();
        if (currentClass != null)
            classList.getSelectionModel().select(helper.getCurrentClass());
    }
}
