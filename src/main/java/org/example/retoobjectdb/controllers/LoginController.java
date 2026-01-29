package org.example.retoobjectdb.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.retoobjectdb.models.User;
import org.example.retoobjectdb.repositories.UserRepository;
import org.example.retoobjectdb.session.AuthService;
import org.example.retoobjectdb.session.SimpleSessionService;
import org.example.retoobjectdb.utils.JavaFXUtil;
import org.example.retoobjectdb.utils.JPAUtil;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @javafx.fxml.FXML
    private TextField txtUsername;
    @javafx.fxml.FXML
    private PasswordField txtPassword;
    @javafx.fxml.FXML
    private Label info;

    private UserRepository userRepository;
    private AuthService authService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userRepository = new UserRepository(JPAUtil.getEntityManagerFactory());
        authService = new AuthService(userRepository);

    }

    @javafx.fxml.FXML
    public void login(ActionEvent actionEvent) {
        Optional<User> user = authService.validateUser(txtUsername.getText(),txtPassword.getText() );
        if (user.isPresent()){
            SimpleSessionService sessionService = new SimpleSessionService();
            sessionService.login(user.get());
            sessionService.setObject("id", user.get().getId());
            JavaFXUtil.setScene("/org/example/retoobjectdb/main-view.fxml");
        }
        else  {
            info.setText("Incorrect username or password");
        }

    }

    @javafx.fxml.FXML
    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }
}
