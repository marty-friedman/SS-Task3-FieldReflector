package com.egrasoft.ss.fieldreflector;

import com.egrasoft.ss.fieldreflector.service.FrameService;
import javafx.application.Application;
import javafx.stage.Stage;

public class FieldReflectorApplication extends Application {
    private FrameService frameService = FrameService.getInstance();

    @Override
    public void start(Stage stage) throws Exception {
        frameService.initMainFrame(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
