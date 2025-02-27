package by.rublevskaya.mvcapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class StartController {

    @FXML
    private Button continueButton;

    public StartController(Button continueButton) {
        this.continueButton = continueButton;
    }

    @FXML
    private void onContinueButtonClick(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
            Scene mainScene = new Scene(fxmlLoader.load(), 800, 600);
            Stage stage = (Stage) continueButton.getScene().getWindow();
            stage.setTitle("ГЛАВНОЕ ОКОШКО КАТЮШИ!!!");
            stage.setScene(mainScene);
            String css = getClass().getResource("/css/styles.css").toExternalForm();
            mainScene.getStylesheets().add(css);
            stage.setScene(mainScene);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Ошибка загрузки файла Main.fxml. Проверьте путь");
        }
    }
}
