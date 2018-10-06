package com.egrasoft.ss.fieldreflector.util.table;

import com.egrasoft.ss.fieldreflector.service.LocalizationService;
import com.egrasoft.ss.fieldreflector.service.ReflectionService;
import com.egrasoft.ss.fieldreflector.util.Constants;
import javafx.beans.property.Property;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.TextFieldTableCell;

import java.lang.reflect.Field;
import java.util.List;

import static com.egrasoft.fxcommons.util.ControllerUtils.createMessageDialog;

public class FieldValueTableCell extends TextFieldTableCell<Field, FieldValue<?>> {
    private static FieldValueStringConverter converter = new FieldValueStringConverter();

    private ReflectionService reflectionService = ReflectionService.getInstance();
    private LocalizationService localizationService = LocalizationService.getInstance();

    private Property<Object> instanceProperty;

    public FieldValueTableCell(Property<Object> instanceProperty) {
        super(converter);
        this.instanceProperty = instanceProperty;
        itemProperty().addListener((observable, oldValue, newValue) ->
                setEditable(newValue != null && newValue.isEditable()));
    }

    @Override
    public void updateItem(FieldValue item, boolean empty) {
        if (item == null || empty || getItem() == null) {
            super.updateItem(item, empty);
            return;
        }

        Class<?> objClass = getItem().getObjClass();
        List<Field> fields = getTableView().getItems();
        int index = getIndex();
        Field field = null;
        if (index < fields.size() && index >= 0)
            field = fields.get(index);

        if (item instanceof RawFieldValue) {
            try {
                item = FieldValue.convertRaw((String) item.getObject(), objClass);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                displayError(Constants.Dialogs.ERROR_CONVERSION_EXCEPTION);
                item = getItem();
            }
        }

        if (field != null && field.getType() == objClass) {
            try {
                reflectionService.setFieldValue(instanceProperty.getValue(), field, item.getObject());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                displayError(Constants.Dialogs.ERROR_ILLEGAL_ACCESS_CONTENT_TEXT_KEY);
                item = getItem();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                displayError(Constants.Dialogs.ERROR_CONVERSION_EXCEPTION);
                item = getItem();
            }
        }
        super.updateItem(item, false);
    }

    private void displayError(String key) {
        createMessageDialog(Alert.AlertType.ERROR, localizationService.getString(Constants.Dialogs.ERROR_TITLE_KEY),
                localizationService.getString(key)).showAndWait();
    }
}
