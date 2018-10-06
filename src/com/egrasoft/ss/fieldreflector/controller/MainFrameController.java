package com.egrasoft.ss.fieldreflector.controller;

import com.egrasoft.ss.fieldreflector.javafx.ClassListView;
import com.egrasoft.ss.fieldreflector.javafx.FieldTableView;
import com.egrasoft.ss.fieldreflector.service.LocalizationService;
import com.egrasoft.ss.fieldreflector.service.ReflectionService;
import com.egrasoft.ss.fieldreflector.util.Constants;
import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.egrasoft.fxcommons.util.ControllerUtils.createMessageDialog;
import static com.egrasoft.fxcommons.util.ControllerUtils.createTextInputDialog;

public class MainFrameController {
    private LocalizationService localizationService = LocalizationService.getInstance();
    private ReflectionService reflectionService = ReflectionService.getInstance();

    @FXML
    private ClassListView classList;
    @FXML
    private FieldTableView fieldTable;

    private ObservableList<Class<?>> classes = FXCollections.observableArrayList();
    private Map<Class<?>, Object> instanceMap = new HashMap<>();
    private Property<Object> instanceProperty = new SimpleObjectProperty<>();

    @FXML
    @SuppressWarnings("unchecked")
    private void initialize() {
        classList.initialize(classes, instanceMap, instanceProperty);
        fieldTable.initialize(instanceProperty);
    }

    @FXML
    private void doQuit() {
        Platform.exit();
    }

    @FXML
    private void doAbout() {
        createMessageDialog(Alert.AlertType.INFORMATION,
                localizationService.getString(Constants.Dialogs.ABOUT_TITLE_KEY),
                localizationService.getString(Constants.Dialogs.ABOUT_CONTENT_TEXT_KEY)).showAndWait();
    }

    @FXML
    private void doAddClass() {
        createTextInputDialog(localizationService.getString(Constants.Dialogs.ADD_CLASS_TITLE_KEY),
                localizationService.getString(Constants.Dialogs.ADD_CLASS_CONTENT_TEXT_KEY),
                "com.example.SampleBean").showAndWait()
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
    private void doChangeLanguage() {

    }

    private void tryAddClass(String className) {
        Class<?> objClass = null;
        try {
            objClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            createMessageDialog(Alert.AlertType.ERROR,
                    localizationService.getString(Constants.Dialogs.ERROR_TITLE_KEY),
                    localizationService.getString(Constants.Dialogs.ERROR_CLASS_NOT_FOUND_CONTENT_TEXT_KEY)).showAndWait();
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
                    localizationService.getString(errorKey)).showAndWait();
    }
}
