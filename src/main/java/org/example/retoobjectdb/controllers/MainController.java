package org.example.retoobjectdb.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import org.example.retoobjectdb.models.Copy;
import org.example.retoobjectdb.models.Film;
import org.example.retoobjectdb.models.User;
import org.example.retoobjectdb.repositories.CopyRespository;
import org.example.retoobjectdb.repositories.FilmRepository;
import org.example.retoobjectdb.services.UserService;
import org.example.retoobjectdb.session.SimpleSessionService;
import org.example.retoobjectdb.utils.DataProvider;
import org.example.retoobjectdb.utils.JavaFXUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Label lblUsuario;
    @FXML
    private TableColumn<Copy, String> cTitulo;
    @FXML
    private Button btnBorrar;
    @FXML
    private TableColumn<Copy, String> cSoporte;
    @FXML
    private TableView<Copy> tabla;
    @FXML
    private TableColumn<Copy, String> cEstado;
    @FXML
    private TableColumn<Copy, String> cId;

    SimpleSessionService simpleSessionService = new SimpleSessionService();
    FilmRepository filmRepository = new FilmRepository(DataProvider.getSessionFactory());
    CopyRespository copyRespository = new CopyRespository(DataProvider.getSessionFactory());
    UserService userService = new UserService();

    @FXML
    private Button btnCerrarSesion;
    @FXML
    private Button btnSalir;
    @FXML
    private Button btnAñadirCopia;
    @FXML
    private FlowPane panelUsuario;
    @FXML
    private Button btnAñadirPelicula;
    @FXML
    private FlowPane panelAdmin;
    @FXML
    private Button btnCerrarSesion1;
    @FXML
    private Button btnSalir1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        lblUsuario.setText("Bienvenido: " + simpleSessionService.getActive().getUsername());

        cId.setCellValueFactory((row) -> {
            return new SimpleStringProperty(String.valueOf(row.getValue().getId()));
        });
        cTitulo.setCellValueFactory((row) -> {
            String title = row.getValue().getFilm().getTitle();
            return new SimpleStringProperty(title);
        });

        cSoporte.setCellValueFactory((row) -> {
            return new SimpleStringProperty(String.valueOf(row.getValue().getSoporte()));
        });
        cEstado.setCellValueFactory((row) -> {
            return new SimpleStringProperty(String.valueOf(row.getValue().getEstado()));
        });

        tabla.getSelectionModel().selectedItemProperty().addListener(showCopy());

        simpleSessionService.getActive().getCopies().forEach(copy -> {
            tabla.getItems().add(copy);
        });

        if (simpleSessionService.getActive().getIsAdmin() == true) {
            panelAdmin.setVisible(true);
            panelUsuario.setVisible(false);
        } else {
            panelAdmin.setVisible(false);
            panelUsuario.setVisible(true);
        }

        tabla.getSelectionModel().selectedItemProperty().addListener(showCopy());

        simpleSessionService.getActive().getCopies().forEach(copy -> {
            tabla.getItems().add(copy);
        });


    }

    private ChangeListener<Copy> showCopy() {
        return (obs, old, news) -> {
            if (news != null) {
                JavaFXUtil.showModal(
                        Alert.AlertType.INFORMATION,
                        news.getFilm().getTitle(),
                        news.getFilm().getTitle(),
                        news.toString()
                );

            }
        };
    }

    @FXML
    public void borrar(ActionEvent actionEvent) {
        if (tabla.getSelectionModel().getSelectedItem() == null) return;
        Copy selectedCopy = tabla.getSelectionModel().getSelectedItem();

        User user = userService.deleteCopyFromUser(simpleSessionService.getActive(), selectedCopy);
        simpleSessionService.update(user);

        tabla.getItems().clear();
        simpleSessionService.getActive().getCopies().forEach(game -> {
            tabla.getItems().add(game);
        });
    }


    @FXML
    public void cerrarSesion(ActionEvent actionEvent) {
        SimpleSessionService sessionService = new SimpleSessionService();
        sessionService.logout();
        JavaFXUtil.setScene("/org/example/retoobjectdb/login-view.fxml");
    }

    @FXML
    public void salir(ActionEvent actionEvent) {
        System.exit(0);
    }

    @FXML
    public void añadirPelicula(ActionEvent actionEvent) {
        JavaFXUtil.setScene("/org/example/retoobjectdb/peliculas-view.fxml");
    }

    @FXML
    public void añadirCopia(ActionEvent actionEvent) {

        var films = filmRepository.findAll();

        ChoiceDialog<Film> dialog = new ChoiceDialog<>(films.get(0), films);
        dialog.setTitle("Nueva Copia");
        dialog.setHeaderText("Añadir nueva copia a tu colección");
        dialog.setContentText("Selecciona la película:");
        var result = dialog.showAndWait();
        if (result.isPresent()) {
            Film selectedFilm = result.get();

            ChoiceDialog<String> soporteDialog = new ChoiceDialog<>( "Soporte", "DVD","BluRay");

            soporteDialog.setTitle("Soporte");
            soporteDialog.setHeaderText("Formato de la copia");
            soporteDialog.setContentText("Elige el soporte:");
            var soporteResult = soporteDialog.showAndWait();

            ChoiceDialog<String> estadoDialog = new ChoiceDialog<>( "Estado", "Bueno","Dañado");

            soporteDialog.setTitle("Estado");
            soporteDialog.setHeaderText("Estado de la copia");
            soporteDialog.setContentText("Elige el estado:");
            var estadoResult = estadoDialog.showAndWait();

            String soporteElegido = soporteResult.orElse("");
            String estadoElegido = estadoResult.orElse("");

            Copy newCopy = new Copy();
            newCopy.setFilm(selectedFilm);
            newCopy.setUser(simpleSessionService.getActive());
            newCopy.setSoporte(soporteElegido);
            newCopy.setEstado(estadoElegido);

            User userActualizado = userService.createNewCopy(newCopy, simpleSessionService.getActive());

            simpleSessionService.update(userActualizado);
            refreshTable();

            JavaFXUtil.showModal(Alert.AlertType.INFORMATION, "Copia Añadida", "Éxito", "Has añadido " + selectedFilm.getTitle() + " a tu colección.");
        }
    }
    private void refreshTable() {
        tabla.getItems().clear();
        tabla.getItems().addAll(simpleSessionService.getActive().getCopies());
    }
}
