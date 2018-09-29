package com.egrasoft.ss.fieldreflector.controller;

import com.egrasoft.ss.fieldreflector.service.LocalizationService;
import com.egrasoft.ss.fieldreflector.util.Constants;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import static com.egrasoft.fxcommons.util.ControllerUtils.createMessageDialog;

public class MainFrameController {
    private LocalizationService localizationService = LocalizationService.getInstance();

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
}
