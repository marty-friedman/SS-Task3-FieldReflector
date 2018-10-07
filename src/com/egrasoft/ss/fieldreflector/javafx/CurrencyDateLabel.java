package com.egrasoft.ss.fieldreflector.javafx;

import com.egrasoft.ss.fieldreflector.service.LocalizationService;
import com.egrasoft.ss.fieldreflector.util.Constants;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.Objects;

public class CurrencyDateLabel extends Label {
    private LocalizationService localizationService = LocalizationService.getInstance();

    public CurrencyDateLabel() throws IOException {
        URL view = getClass().getClassLoader().getResource(Constants.Location.CURRENCY_DATE_LABEL_LOCATION);
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(view));
        loader.setController(this);
        loader.setRoot(this);
        loader.load();
    }

    public void initialize() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            setText(localizationService.getDateTimeFormatter().format(ZonedDateTime.now()) + "\t" +
                    localizationService.getDefaultCurrency().getCurrencyCode());
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
