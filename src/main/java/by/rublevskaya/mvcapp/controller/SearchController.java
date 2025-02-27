package by.rublevskaya.mvcapp.controller;

import by.rublevskaya.mvcapp.model.DatabaseHandler;
import by.rublevskaya.mvcapp.model.Record;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

public class SearchController {

    @FXML private TextField fullNameField;
    @FXML private TextField positionField;
    @FXML private TextField teamField;
    @FXML private TableView<Record> searchResults;
    @FXML private TableColumn<Record, Integer> idColumn;
    @FXML private TableColumn<Record, String> fullNameColumn;
    @FXML private TableColumn<Record, String> birthDateColumn;
    @FXML private TableColumn<Record, String> teamColumn;
    @FXML private TableColumn<Record, String> homeCityColumn;
    @FXML private TableColumn<Record, String> squadColumn;
    @FXML private TableColumn<Record, String> positionColumn;

    private final DatabaseHandler dbHandler = new DatabaseHandler();

    public SearchController(TextField fullNameField, TextField positionField, TableView<Record> searchResults, TableColumn<Record, Integer> idColumn,
                            TableColumn<Record, String> fullNameColumn, TableColumn<Record, String> birthDateColumn, TableColumn<Record, String> teamColumn,
                            TableColumn<Record, String> homeCityColumn, TableColumn<Record, String> squadColumn, TableColumn<Record, String> positionColumn) {
        this.fullNameField = fullNameField;
        this.positionField = positionField;
        this.searchResults = searchResults;
        this.idColumn = idColumn;
        this.fullNameColumn = fullNameColumn;
        this.birthDateColumn = birthDateColumn;
        this.teamColumn = teamColumn;
        this.homeCityColumn = homeCityColumn;
        this.squadColumn = squadColumn;
        this.positionColumn = positionColumn;
        this.teamField = new TextField();
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        teamColumn.setCellValueFactory(new PropertyValueFactory<>("team"));
        homeCityColumn.setCellValueFactory(new PropertyValueFactory<>("homeCity"));
        squadColumn.setCellValueFactory(new PropertyValueFactory<>("squad"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
    }

    @FXML
    public void search() {
        String fullName = fullNameField.getText().trim();
        String position = positionField.getText().trim();
        String team = teamField.getText().trim();
        if (fullName.isEmpty() && position.isEmpty() && team.isEmpty()) {
            showAlert("Заполните хотя бы одно поле для поиска!");
            return;
        }
        List<Record> results = dbHandler.searchRecords(fullName, null, position, null, team, null);
        if (results.isEmpty()) {
            showAlert("Записи по заданным условиям не найдены");
        } else {
            searchResults.getItems().setAll(results);
        }
    }

    @FXML
    public void closeDialog() {
        Stage stage = (Stage) searchResults.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Результат поиска");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}