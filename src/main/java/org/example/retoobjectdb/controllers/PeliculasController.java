package org.example.retoobjectdb.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.retoobjectdb.models.Film;
import org.example.retoobjectdb.repositories.FilmRepository;
import org.example.retoobjectdb.utils.JavaFXUtil;
import org.example.retoobjectdb.utils.JPAUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class PeliculasController implements Initializable {

    @javafx.fxml.FXML
    private TableColumn<Film,String> cTitulo;
    @javafx.fxml.FXML
    private TableColumn<Film,String> cGenero;
    @javafx.fxml.FXML
    private TextField txtDirector;
    @javafx.fxml.FXML
    private TextField txtAño;
    @javafx.fxml.FXML
    private TextField txtGenero;
    @javafx.fxml.FXML
    private TableColumn<Film,String> cAño;
    @javafx.fxml.FXML
    private Button btnCancelar;
    @javafx.fxml.FXML
    private TextField txtDescripcion;
    @javafx.fxml.FXML
    private Button btnAñadir;
    @javafx.fxml.FXML
    private TextField txtTitulo;
    @javafx.fxml.FXML
    private TableColumn<Film,String> cId;
    @javafx.fxml.FXML
    private TableView<Film> tabla;
    @javafx.fxml.FXML
    private TableColumn<Film,String> cDescripcion;
    @javafx.fxml.FXML
    private TableColumn<Film,String> cDirector;

    private FilmRepository filmRepository = new FilmRepository(JPAUtil.getEntityManagerFactory());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cId.setCellValueFactory( (row)->{
            return new SimpleStringProperty(String.valueOf(row.getValue().getId()));
        });
        cTitulo.setCellValueFactory( (row)->{
            String title = row.getValue().getTitle();
            return new SimpleStringProperty(title);
        });
        cGenero.setCellValueFactory( (row)->{
            String genre = row.getValue().getGenre();
            return new SimpleStringProperty(genre);
        });
        cAño.setCellValueFactory( (row)->{
            Integer year = row.getValue().getYear();
            return new SimpleStringProperty(year.toString());
        });
        cDescripcion.setCellValueFactory( (row)->{
            String desc = row.getValue().getDescription();
            return new SimpleStringProperty(desc);
        });
        cDirector.setCellValueFactory( (row)->{
            String dir = row.getValue().getDirector();
            return new SimpleStringProperty(dir);
        });

        refreshTable();



    }

    private void refreshTable() {
        tabla.getItems().clear();
        tabla.getItems().addAll(filmRepository.findAll());
    }

    private ChangeListener<Film> showFilm() {
        return (obs, old, news) -> {
            if (news != null) {
                JavaFXUtil.showModal(
                        Alert.AlertType.INFORMATION,
                        news.getTitle(),
                        news.getDescription(),
                        news.toString()
                );
            }
        };
    }

    @javafx.fxml.FXML
    public void cancelar(ActionEvent actionEvent) {
        JavaFXUtil.setScene("/org/example/retoobjectdb/main-view.fxml");
    }

    @javafx.fxml.FXML
    public void añadirPelicula(ActionEvent actionEvent) {

        Film film = new Film();
        film.setTitle(txtTitulo.getText());
        film.setGenre(txtGenero.getText());
        film.setYear(Integer.parseInt(txtAño.getText()));
        film.setDescription(txtDescripcion.getText());
        film.setDirector(txtDirector.getText());

        filmRepository.save(film);
        limpiarForm();
        refreshTable();
    }

    private void limpiarForm() {
        txtTitulo.setText("");
        txtGenero.setText("");
        txtAño.setText("");
        txtDescripcion.setText("");
        txtDirector.setText("");
    }
}
