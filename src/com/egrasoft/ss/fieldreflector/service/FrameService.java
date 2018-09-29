package com.egrasoft.ss.fieldreflector.service;

import com.egrasoft.ss.fieldreflector.controller.MainFrameController;
import com.egrasoft.ss.fieldreflector.util.Constants;
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

    public void initMainFrame(Stage stage) throws IOException {
        Parent root = loadView(Constants.Location.MAIN_FRAME_VIEW_LOCATION, new MainFrameController());
        stage.setTitle(localizationService.getString(Constants.Frame.MAIN_FRAME_TITLE_KEY));
        stage.setScene(new Scene(root));
    }

    private Parent loadView(String location, Object controller) throws IOException {
        URL view = getClass().getClassLoader().getResource(location);
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(view));
        loader.setController(controller);
        loader.setResources(localizationService.getCurrentBundle());
        return loader.load();
    }

    public static FrameService getInstance() {
        return SingletonHelper.instance;
    }

    private static class SingletonHelper {
        private static final FrameService instance = new FrameService();
    }
}
