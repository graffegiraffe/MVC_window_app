package by.rublevskaya.mvcapp.controller;

import by.rublevskaya.mvcapp.model.DatabaseHandler;
import by.rublevskaya.mvcapp.model.Record;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class MainController {

    @FXML private TableView<Record> recordTable;
    @FXML private TableColumn<Record, Integer> idColumn;
    @FXML private TableColumn<Record, String> fullNameColumn;
    @FXML private TableColumn<Record, String> birthDateColumn;
    @FXML private TableColumn<Record, String> teamColumn;
    @FXML private TableColumn<Record, String> homeCityColumn;
    @FXML private TableColumn<Record, String> squadColumn;
    @FXML private TableColumn<Record, String> positionColumn;
    @FXML private javafx.scene.control.TreeView<String> recordTreeView;
    @FXML private javafx.scene.control.TabPane viewSwitcher;
    @FXML private javafx.scene.control.Label currentPageLabel;
    @FXML private javafx.scene.control.Label totalPagesLabel;
    @FXML private javafx.scene.control.ChoiceBox<Integer> recordsPerPageChoiceBox;

    private final DatabaseHandler dbHandler = new DatabaseHandler();
    private int currentPage = 1;
    private int recordsPerPage = 10;
    private int totalRecords = 0;
    private int totalPages = 1;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        teamColumn.setCellValueFactory(new PropertyValueFactory<>("team"));
        homeCityColumn.setCellValueFactory(new PropertyValueFactory<>("homeCity"));
        squadColumn.setCellValueFactory(new PropertyValueFactory<>("squad"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));

        recordsPerPageChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldValue, newValue) -> {
            if (newValue != null) {
                recordsPerPage = newValue;
                currentPage = 1;
                refreshView();
            }
        });

        recordsPerPageChoiceBox.setValue(recordsPerPage);
        refreshView();
        setupTabCloseHandler();
    }

    @FXML
    public void returnToStart() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Start.fxml"));
            Stage stage = (Stage) viewSwitcher.getScene().getWindow();
            Scene startScene = new Scene(fxmlLoader.load(), 800, 600);
            startScene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
            stage.setScene(startScene);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Ошибка: не удалось вернуться на стартовую страницу");
        }
    }

    @FXML
    public void goToFirstPage() {
        currentPage = 1;
        refreshView();
    }

    @FXML
    public void goToPreviousPage() {
        if (currentPage > 1) {
            currentPage--;
            refreshView();
        }
    }

    @FXML
    public void goToNextPage() {
        if (currentPage < totalPages) {
            currentPage++;
            refreshView();
        }
    }

    @FXML
    public void goToLastPage() {
        currentPage = totalPages;
        refreshView();
    }

    @FXML
    public void loadRecords() {
        recordTable.getItems().setAll(dbHandler.getAllRecords());
    }

    @FXML
    public void openAddDialog() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AddDialog.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            String css = getClass().getResource("/css/styles.css").toExternalForm();
            scene.getStylesheets().add(css);
            Stage stage = new Stage();
            stage.setTitle("Добавить новую игровую КАТЮШУ");
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Ошибка загрузки AddDialog.fxml. Проверьте путь");
        }
    }

    @FXML
    public void openDeleteDialog() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/DeleteDialog.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            String css = getClass().getResource("/css/styles.css").toExternalForm();
            scene.getStylesheets().add(css);
            Stage stage = new Stage();
            stage.setTitle("Удаление записей, ЭХ БЛИН");
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Ошибка загрузки DeleteDialog.fxml. Проверьте путь");
        }
    }

    @FXML
    public void openSearchDialog() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SearchDialog.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            String css = getClass().getResource("/css/styles.css").toExternalForm();
            scene.getStylesheets().add(css);
            Stage stage = new Stage();
            stage.setTitle("Поиск записей");
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Ошибка загрузки SearchDialog.fxml. Проверьте путь к файлу");
        }
    }

    @FXML
    public void openXMLMenu() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/XMLMenu.fxml"));
            Scene xmlMenuScene = new Scene(fxmlLoader.load(), 800, 600);
            String css = getClass().getResource("/css/styles.css").toExternalForm();
            xmlMenuScene.getStylesheets().add(css);
            Stage stage = new Stage();
            stage.setTitle("Работа с XML((((");
            stage.setScene(xmlMenuScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Ошибка загрузки окна для работы с XML");
        }
    }

    private void updateRecordTable() {
        totalRecords = dbHandler.getAllRecords().size();
        totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);
        totalPagesLabel.setText(String.valueOf(totalPages));
        currentPageLabel.setText(String.valueOf(currentPage));
        int fromIndex = (currentPage - 1) * recordsPerPage;
        int toIndex = Math.min(fromIndex + recordsPerPage, totalRecords);
        ObservableList<Record> paginatedRecords = FXCollections.observableArrayList(dbHandler.getAllRecords().subList(fromIndex, toIndex));
        recordTable.setItems(paginatedRecords);
    }

    private void updateTreeView() {
        TreeItem<String> root = new TreeItem<>("Записи");
        List<Record> currentPageRecords = dbHandler.getAllRecords().subList(
                (currentPage - 1) * recordsPerPage,
                Math.min(currentPage * recordsPerPage, totalRecords)
        );
        for (Record record : currentPageRecords) {
            TreeItem<String> recordNode = new TreeItem<>(record.getFullName());
            recordNode.getChildren().add(new TreeItem<>("ID: " + record.getId()));
            recordNode.getChildren().add(new TreeItem<>("Дата рождения: " + record.getBirthDate()));
            recordNode.getChildren().add(new TreeItem<>("Команда: " + record.getTeam()));
            recordNode.getChildren().add(new TreeItem<>("Город: " + record.getHomeCity()));
            recordNode.getChildren().add(new TreeItem<>("Состав: " + record.getSquad()));
            recordNode.getChildren().add(new TreeItem<>("Позиция: " + record.getPosition()));
            root.getChildren().add(recordNode);
        }
        recordTreeView.setRoot(root);
        recordTreeView.setShowRoot(false);
    }

    private void handleTabClosed(Tab closedTab) {
        String message = "Вкладка \"" + closedTab.getText() + "\" была закрыта";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Закрытие вкладки");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

        if (viewSwitcher.getTabs().isEmpty()) {
            System.out.println("Все вкладки закрыты");
            returnToStart();
        } else {
            viewSwitcher.getSelectionModel().select(0);
        }
    }

    private void refreshView() {
        updateRecordTable();
        updateTreeView();
    }

    private void setupTabCloseHandler() {
        viewSwitcher.getTabs().forEach(tab -> {
            tab.setOnClosed(event -> handleTabClosed(tab));
        });
    }
}