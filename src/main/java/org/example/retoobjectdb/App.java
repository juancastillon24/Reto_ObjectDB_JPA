package org.example.retoobjectdb;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.retoobjectdb.utils.JavaFXUtil;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        JavaFXUtil.initStage(stage);
        JavaFXUtil.setScene("/org/example/retoobjectdb/login-view.fxml");
    }
}
