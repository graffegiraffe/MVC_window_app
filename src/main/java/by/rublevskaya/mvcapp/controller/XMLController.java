package by.rublevskaya.mvcapp.controller;

import by.rublevskaya.mvcapp.model.Record;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.time.LocalDate;

public class XMLController {
    @FXML private TableColumn<Record, Integer> idColumn;
    @FXML private TableColumn<Record, String> fullNameColumn;
    @FXML private TableColumn<Record, String> birthDateColumn;
    @FXML private TableColumn<Record, String> teamColumn;
    @FXML private TableColumn<Record, String> homeCityColumn;
    @FXML private TableColumn<Record, String> squadColumn;
    @FXML private TableColumn<Record, String> positionColumn;
    @FXML private TableView<Record> xmlRecordTable;
    private final ObservableList<Record> records = FXCollections.observableArrayList();

    @FXML
    public void saveToXML() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Сохранить в XML");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files", "*.xml"));
            File file = fileChooser.showSaveDialog(new Stage());
            if (file != null) {
                saveRecordsToXML(file);
                showAlert("Успех", "Данные успешно сохранены в файл: " + file.getName(), Alert.AlertType.INFORMATION);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Ошибка", "Ошибка при сохранении в XML: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void loadFromXML() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Загрузить из XML");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files", "*.xml"));
            File file = fileChooser.showOpenDialog(new Stage());

            if (file != null) {
                loadRecordsFromXML(file);
                xmlRecordTable.setItems(records); // Обновляем таблицу новыми данными
                showAlert("Успех", "Данные успешно загружены из файла: " + file.getName(), Alert.AlertType.INFORMATION);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Ошибка", "Ошибка при загрузке из XML: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    @FXML
    public void initialize() {
        // Настройка привязки колонок к полям объекта Record
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        teamColumn.setCellValueFactory(new PropertyValueFactory<>("team"));
        homeCityColumn.setCellValueFactory(new PropertyValueFactory<>("homeCity"));
        squadColumn.setCellValueFactory(new PropertyValueFactory<>("squad"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        xmlRecordTable.setItems(records);
    }
    @FXML
    public void returnToMainMenu() {
        Stage stage = (Stage) xmlRecordTable.getScene().getWindow();
        stage.close();
    }

    private void saveRecordsToXML(File file) throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();
        Element rootElement = doc.createElement("Records");
        doc.appendChild(rootElement);

        for (Record record : records) {
            Element recordElement = doc.createElement("Record");

            addElementWithText(doc, recordElement, "ID", String.valueOf(record.getId()));
            addElementWithText(doc, recordElement, "FullName", record.getFullName());
            addElementWithText(doc, recordElement, "BirthDate", record.getBirthDate().toString());
            addElementWithText(doc, recordElement, "Team", record.getTeam());
            addElementWithText(doc, recordElement, "HomeCity", record.getHomeCity());
            addElementWithText(doc, recordElement, "Squad", record.getSquad());
            addElementWithText(doc, recordElement, "Position", record.getPosition());
            rootElement.appendChild(recordElement);
        }
        javax.xml.transform.TransformerFactory transformerFactory = javax.xml.transform.TransformerFactory.newInstance();
        javax.xml.transform.Transformer transformer = transformerFactory.newTransformer();
        javax.xml.transform.dom.DOMSource domSource = new javax.xml.transform.dom.DOMSource(doc);
        javax.xml.transform.stream.StreamResult streamResult = new javax.xml.transform.stream.StreamResult(file);
        transformer.transform(domSource, streamResult);
    }

    private void loadRecordsFromXML(File file) throws Exception {
        records.clear();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        document.getDocumentElement().normalize();
        var recordNodes = document.getElementsByTagName("record");

        for (int i = 0; i < recordNodes.getLength(); i++) {
            Element recordElement = (Element) recordNodes.item(i);

            int id = Integer.parseInt(recordElement.getElementsByTagName("id").item(0).getTextContent());
            String fullName = recordElement.getElementsByTagName("fullName").item(0).getTextContent();
            LocalDate birthDate = LocalDate.parse(recordElement.getElementsByTagName("birthDate").item(0).getTextContent());
            String team = recordElement.getElementsByTagName("team").item(0).getTextContent();
            String homeCity = recordElement.getElementsByTagName("homeCity").item(0).getTextContent();
            String squad = recordElement.getElementsByTagName("squad").item(0).getTextContent();
            String position = recordElement.getElementsByTagName("position").item(0).getTextContent();
            Record record = new Record(id, fullName, birthDate, team, homeCity, squad, position);
            records.add(record);
        }
    }

    private void addElementWithText(Document doc, Element parent, String tagName, String textContent) {
        Element element = doc.createElement(tagName);
        element.appendChild(doc.createTextNode(textContent));
        parent.appendChild(element);
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}