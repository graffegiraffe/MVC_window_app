package by.rublevskaya.mvcapp.controller;

import by.rublevskaya.mvcapp.model.DatabaseHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DeleteController {

    @FXML private TextField fullNameField;
    @FXML private TextField positionField;

    private final DatabaseHandler dbHandler = new DatabaseHandler();

    public DeleteController(TextField fullNameField, TextField positionField) {
        this.fullNameField = fullNameField;
        this.positionField = positionField;
    }

    @FXML
    public void delete() {
        String fullName = fullNameField.getText().trim();
        String position = positionField.getText().trim();

        if (fullName.isEmpty() && position.isEmpty()) {
            showAlert("Заполните хотя бы одно поле для удаления записей!");
            return;
        }

        int deletedCount = dbHandler.deleteRecords(fullName, null, position, null, null, null);
        String message = (deletedCount > 0)
                ? "Удалено записей: " + deletedCount
                : "Записи по заданным условиям не найдены.";

        showAlert(message);
        fullNameField.clear();
        positionField.clear();
    }

    @FXML
    public void closeDialog() {
        Stage stage = (Stage) fullNameField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Результат удаления");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}