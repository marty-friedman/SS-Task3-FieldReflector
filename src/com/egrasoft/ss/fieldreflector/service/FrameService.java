package com.egrasoft.ss.fieldreflector.service;

import com.egrasoft.ss.fieldreflector.controller.MainFrameController;
import com.egrasoft.ss.fieldreflector.util.Constants;
import com.egrasoft.ss.fieldreflector.util.PersistenceHelper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class FrameService {
    private LocalizationService localizationService = LocalizationService.getInstance();

    private FrameService() {
    }

    public void loadMainFrame(Stage stage) throws IOException {
        loadMainFrameInternal(stage, new MainFrameController(stage));
    }

    public void reloadMainFrame(Stage stage, PersistenceHelper helper) throws IOException {
        MainFrameController controller = new MainFrameController(stage, helper);
        loadMainFrameInternal(stage, controller);
        controller.restoreFromPersistenceHelperAfterInit(helper);
    }

    private void loadMainFrameInternal(Stage stage, MainFrameController controller) throws IOException {
        URL view = getClass().getClassLoader().getResource(Constants.Location.MAIN_FRAME_VIEW_LOCATION);
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(view));
        loader.setController(controller);
        loader.setClassLoader(getClass().getClassLoader());
        loader.setResources(localizationService.getCurrentBundle());
        Parent root = loader.load();
        stage.setTitle(localizationService.getString(Constants.Frame.MAIN_FRAME_TITLE_KEY));
        stage.setScene(new Scene(root));
    }

    public static FrameService getInstance() {
        return SingletonHelper.instance;
    }

    private static class SingletonHelper {
        private static final FrameService instance = new FrameService();
    }
}
