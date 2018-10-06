package com.egrasoft.ss.fieldreflector.javafx;

import com.egrasoft.ss.fieldreflector.service.LocalizationService;
import com.egrasoft.ss.fieldreflector.service.ReflectionService;
import com.egrasoft.ss.fieldreflector.util.Constants;
import com.egrasoft.ss.fieldreflector.util.table.FieldValue;
import com.egrasoft.ss.fieldreflector.util.table.FieldValueTableCell;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Objects;

import static com.egrasoft.fxcommons.util.ControllerUtils.prepareTableColumn;

public class FieldTableView extends TableView<Field> {
    private LocalizationService localizationService = LocalizationService.getInstance();
    private ReflectionService reflectionService = ReflectionService.getInstance();

    @FXML
    private TableColumn<Field, String> fieldNameColumn;
    @FXML
    private TableColumn<Field, String> fieldTypeColumn;
    @FXML
    private TableColumn<Field, FieldValue<?>> fieldValueColumn;

    public FieldTableView() throws IOException {
        URL view = getClass().getClassLoader().getResource(Constants.Location.FIELD_TABLE_VIEW_LOCATION);
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(view));
        loader.setController(this);
        loader.setResources(localizationService.getCurrentBundle());
        loader.setRoot(this);
        loader.load();
    }

    @SuppressWarnings("unchecked")
    public void initialize(Property<Object> instanceProperty) {
        instanceProperty.addListener((observable, oldValue, newValue) -> {
            if (newValue == null)
                return;
            this.setItems(FXCollections.observableArrayList(reflectionService.getFields(newValue)));
            this.getColumns().get(0).setVisible(false);
            this.getColumns().get(0).setVisible(true);
        });

        prepareTableColumn(fieldNameColumn, Field::getName, null, null, null);
        prepareTableColumn(fieldTypeColumn, field -> field.getType().getCanonicalName(), null, null, null);

        fieldValueColumn.setCellValueFactory(param -> {
            FieldValue<String> fieldValue = new FieldValue<>("<" + localizationService.getString(Constants.Table.VALUE_NOT_ACCESSIBLE) + ">");
            fieldValue.setEditable(false);
            try {
                fieldValue = new FieldValue(reflectionService.getFieldValue(instanceProperty.getValue(), param.getValue()), param.getValue().getType());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return new SimpleObjectProperty<>(fieldValue);
        });
        this.setEditable(true);
        fieldValueColumn.setEditable(true);
        fieldValueColumn.setCellFactory(param -> new FieldValueTableCell(instanceProperty));
    }
}
