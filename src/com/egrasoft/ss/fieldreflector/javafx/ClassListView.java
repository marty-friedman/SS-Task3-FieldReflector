package com.egrasoft.ss.fieldreflector.javafx;

import com.egrasoft.ss.fieldreflector.util.Constants;
import javafx.beans.property.Property;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;

import static com.egrasoft.fxcommons.util.ControllerUtils.prepareListView;

public class ClassListView extends ListView<Class<?>> {
    public ClassListView() throws IOException {
        URL view = getClass().getClassLoader().getResource(Constants.Location.CLASS_LIST_VIEW_LOCATION);
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(view));
        loader.setController(this);
        loader.setRoot(this);
        loader.load();
    }

    public void initialize(ObservableList<Class<?>> classes, Map<Class<?>, Object> instanceMap, Property<Object> instanceProperty) {
        this.setItems(new SortedList<>(classes, Comparator.comparing(Class::getCanonicalName)));
        this.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                instanceProperty.setValue(null);
                instanceProperty.setValue(instanceMap.get(newValue));
            }
        });
        prepareListView(this, Class::getCanonicalName);
    }
}
