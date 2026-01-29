package org.example.retoobjectdb;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.retoobjectdb.utils.JPAUtil;
import org.example.retoobjectdb.utils.JavaFXUtil;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        JPAUtil.getEntityManagerFactory(); // Inicia la conexión con la BD
        JavaFXUtil.initStage(stage);
        JavaFXUtil.setScene("/org/example/retoobjectdb/login-view.fxml");
    }

    @Override
    public void stop() throws Exception {
        JPAUtil.shutdown(); // Cierra la conexión con la BD al salir
    }
}
