package hu.unideb.inf.warehouse.frontend.controller;

import hu.unideb.inf.warehouse.model.Item;
import hu.unideb.inf.warehouse.repository.ItemRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

@Component
public class MainPageController {

    @Autowired
    private ItemRepository itemRepository;

    @FXML private TableView<Item> tableView;
    @FXML private TableColumn<Item, String> colField1;
    @FXML private TableColumn<Item, String> colField2;
    @FXML private TableColumn<Item, String> colField3;
    @FXML private TableColumn<Item, String> colField4;

    @FXML private TextField field1Input;
    @FXML private TextField field2Input;
    @FXML private TextField field3Input;
    @FXML private TextField field4Input;

    @FXML private TextField searchField;

    private ObservableList<Item> data = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colField1.setCellValueFactory(param ->
                new javafx.beans.property.SimpleStringProperty(param.getValue().getField1()));
        colField2.setCellValueFactory(param ->
                new javafx.beans.property.SimpleStringProperty(param.getValue().getField2()));
        colField3.setCellValueFactory(param ->
                new javafx.beans.property.SimpleStringProperty(param.getValue().getField3()));
        colField4.setCellValueFactory(param ->
                new javafx.beans.property.SimpleStringProperty(param.getValue().getField4()));

        data.addAll(itemRepository.findAll());
        tableView.setItems(data);
    }

    @FXML
    public void onAdd() {
        Item item = Item.builder()
                .field1(field1Input.getText())
                .field2(field2Input.getText())
                .field3(field3Input.getText())
                .field4(field4Input.getText())
                .build();

        itemRepository.save(item);

        data.clear();
        data.addAll(itemRepository.findAll());
    }

    @FXML
    public void onRemove() {
        Item selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alert warning = new Alert(Alert.AlertType.WARNING, "Please select an item to remove!");
            warning.showAndWait();
            return;
        }
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this item?", ButtonType.YES, ButtonType.NO);
        confirmation.setTitle("Confirm Delete");
        confirmation.setHeaderText(null);

        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                itemRepository.delete(selected);
                data.remove(selected);
            }
        });
    }

    @FXML
    public void onClear() {
        field1Input.clear();
        field2Input.clear();
        field3Input.clear();
        field4Input.clear();
    }

    @FXML
    public void onSearch() {
        String text = searchField.getText();
        if (text.isBlank()) {
            data.setAll(itemRepository.findAll());
        } else {
            data.setAll(itemRepository.findByField1ContainingIgnoreCase(text));
        }
    }
}
