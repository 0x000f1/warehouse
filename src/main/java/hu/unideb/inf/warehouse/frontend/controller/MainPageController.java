package hu.unideb.inf.warehouse.frontend.controller;

import hu.unideb.inf.warehouse.model.Product;
import hu.unideb.inf.warehouse.repository.ProductRepository;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainPageController {

    @Autowired
    private ProductRepository productRepository;

    @FXML private TableView<Product> tableView;

    @FXML private TableColumn<Product, String> colField1;
    @FXML private TableColumn<Product, String> colField2;
    @FXML private TableColumn<Product, Number> colField3;
    @FXML private TableColumn<Product, Number> colField4;

    @FXML private TextField field1Input;
    @FXML private TextField field2Input;
    @FXML private TextField field3Input;
    @FXML private TextField field4Input;

    @FXML private TextField searchField;

    private ObservableList<Product> data = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colField1.setCellValueFactory(param ->
                new SimpleStringProperty(param.getValue().getName()));
        colField2.setCellValueFactory(param ->
                new SimpleStringProperty(param.getValue().getCategory()));
        colField3.setCellValueFactory(param ->
                new SimpleDoubleProperty(param.getValue().getPrice()));
        colField4.setCellValueFactory(param ->
                new SimpleIntegerProperty(param.getValue().getStock()));

        data.addAll(productRepository.findAll());
        tableView.setItems(data);
    }

    @FXML
    public void onAdd() {
        try {
            Product p = Product.builder()
                    .name(field1Input.getText())
                    .category(field2Input.getText())
                    .price(Double.parseDouble(field3Input.getText()))
                    .stock(Integer.parseInt(field4Input.getText()))
                    .build();

            productRepository.save(p);

            data.setAll(productRepository.findAll());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Invalid numeric values!").showAndWait();
        }
    }

    @FXML
    public void onRemove() {
        Product selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alert warning = new Alert(Alert.AlertType.WARNING, "Please select a product to remove!");
            warning.showAndWait();
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this product?", ButtonType.YES, ButtonType.NO);

        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                productRepository.delete(selected);
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
        String text = searchField.getText().toLowerCase().trim();

        if (text.isBlank()) {
            data.setAll(productRepository.findAll());
            return;
        }

        data.setAll(
                productRepository.findAll().stream()
                        .filter(p ->
                                (p.getName() != null && p.getName().toLowerCase().contains(text)) ||
                                        (p.getCategory() != null && p.getCategory().toLowerCase().contains(text)) ||

                                        (p.getPrice() != null &&
                                                String.valueOf(p.getPrice()).toLowerCase().contains(text)) ||

                                        (p.getStock() != null &&
                                                String.valueOf(p.getStock()).toLowerCase().contains(text))
                        )
                        .toList()
        );
    }


    @FXML
    public void selectProducts() {
        // Products már működik, csak frissítjük
        data.setAll(productRepository.findAll());
        tableView.setItems(data);

    }

    @FXML
    public void selectOrders() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Orders view is not implemented yet.");
        alert.showAndWait();
        field1Input.setPromptText("ID");
        field2Input.setPromptText("Customer ID");
        field3Input.setPromptText("Order Date");
        field4Input.setPromptText("Status");
    }

    @FXML
    public void selectSales() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Sales view is not implemented yet.");
        alert.showAndWait();
    }

    @FXML
    public void selectCustomers() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Customers view is not implemented yet.");
        alert.showAndWait();
        field1Input.setPromptText("ID");
        field2Input.setPromptText("Name");
        field3Input.setPromptText("E-mail");
        field4Input.setPromptText("Phone");
    }
}
