package by.rublevskaya.mvcapp.controller;

import by.rublevskaya.mvcapp.model.DatabaseHandler;
import by.rublevskaya.mvcapp.model.Record;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class AddController {

    @FXML private TextField fullNameField;
    @FXML private DatePicker birthDatePicker;
    @FXML private TextField teamField;
    @FXML private TextField homeCityField;
    @FXML private TextField squadField;
    @FXML private TextField positionField;

    private final DatabaseHandler dbHandler = new DatabaseHandler();

    public AddController(TextField fullNameField, DatePicker birthDatePicker, TextField teamField, TextField homeCityField, TextField squadField, TextField positionField) {
        this.fullNameField = fullNameField;
        this.birthDatePicker = birthDatePicker;
        this.teamField = teamField;
        this.homeCityField = homeCityField;
        this.squadField = squadField;
        this.positionField = positionField;
    }

    @FXML
    public void addRecord() {
        try {
            System.out.println("Нажата кнопка добавить");
            System.out.println(">>> Добавление нового игрока началось");

            String fullName = fullNameField.getText().trim();
            LocalDate birthDate = birthDatePicker.getValue();
            String team = teamField.getText().trim();
            String homeCity = homeCityField.getText().trim();
            String squad = squadField.getText().trim();
            String position = positionField.getText().trim();

            System.out.println("Полученные данные: ");
            System.out.println("ФИО: " + fullName);
            System.out.println("Дата рождения: " + birthDate);
            System.out.println("Команда: " + team);
            System.out.println("Город: " + homeCity);
            System.out.println("Состав: " + squad);
            System.out.println("Позиция: " + position);

            if (fullName.isEmpty() || birthDate == null || team.isEmpty() || homeCity.isEmpty() ||
                    squad.isEmpty() || position.isEmpty()) {
                showAlert("Все поля обязательны для заполнения.");
                return;
            }
            Record newRecord = new Record(0, fullName, birthDate, team, homeCity, squad, position);
            dbHandler.addRecord(newRecord);
            System.out.println("Игрок успешно добавлен: " + newRecord);
            showAlert("Игрок успешно добавлен!");
            closeDialog();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Произошла ошибка при добавлении записи. Проверьте данные.");
        }
    }

    @FXML
    public void closeDialog() {
        Stage stage = (Stage) fullNameField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Уведомление");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}