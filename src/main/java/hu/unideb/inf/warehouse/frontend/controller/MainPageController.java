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

    @FXML private TableView<Object> tableView;

    @FXML private TableColumn<Object, ?> colField1;
    @FXML private TableColumn<Object, ?> colField2;
    @FXML private TableColumn<Object, ?> colField3;
    @FXML private TableColumn<Object, ?> colField4;

    @FXML private TextField field1Input;
    @FXML private TextField field2Input;
    @FXML private TextField field3Input;
    @FXML private TextField field4Input;

    @FXML private TextField searchField;

    private ObservableList<Object> data = FXCollections.observableArrayList();

    private enum ActiveTable { PRODUCTS, ORDERS, SALES, CUSTOMERS }
    private ActiveTable active = ActiveTable.PRODUCTS;

    @FXML
    public void initialize() {
        loadProductsTable();
        refreshTable();
    }

    private void loadProductsTable() {
        tableView.getColumns().clear();

        TableColumn<Object, String> c1 = new TableColumn<>("Name");
        c1.setCellValueFactory(param ->
                new SimpleStringProperty(((Product) param.getValue()).getName()));

        TableColumn<Object, String> c2 = new TableColumn<>("Category");
        c2.setCellValueFactory(param ->
                new SimpleStringProperty(((Product) param.getValue()).getCategory()));

        TableColumn<Object, Number> c3 = new TableColumn<>("Price");
        c3.setCellValueFactory(param ->
                new SimpleDoubleProperty(((Product) param.getValue()).getPrice()));

        TableColumn<Object, Number> c4 = new TableColumn<>("Stock");
        c4.setCellValueFactory(param ->
                new SimpleIntegerProperty(((Product) param.getValue()).getStock()));

        tableView.getColumns().addAll(c1, c2, c3, c4);

        field1Input.setPromptText("Name");
        field2Input.setPromptText("Category");
        field3Input.setPromptText("Price");
        field4Input.setPromptText("Stock");
    }

    private void loadOrdersTable() {
        tableView.getColumns().clear();

        TableColumn<Object, String> c1 = new TableColumn<>("Order ID");
        TableColumn<Object, String> c2 = new TableColumn<>("Customer ID");
        TableColumn<Object, String> c3 = new TableColumn<>("Date");
        TableColumn<Object, String> c4 = new TableColumn<>("Status");

        tableView.getColumns().addAll(c1, c2, c3, c4);

        field1Input.setPromptText("Order ID");
        field2Input.setPromptText("Customer ID");
        field3Input.setPromptText("Order Date");
        field4Input.setPromptText("Status");

        data.clear();
    }

    private void loadSalesTable() {
        tableView.getColumns().clear();

        TableColumn<Object, String> c1 = new TableColumn<>("Sale ID");
        TableColumn<Object, String> c2 = new TableColumn<>("Product ID");
        TableColumn<Object, String> c3 = new TableColumn<>("Order ID");
        TableColumn<Object, String> c4 = new TableColumn<>("Quantity");

        tableView.getColumns().addAll(c1, c2, c3, c4);

        field1Input.setPromptText("Sale ID");
        field2Input.setPromptText("Product ID");
        field3Input.setPromptText("Order ID");
        field4Input.setPromptText("Quantity");

        data.clear();
    }

    private void loadCustomersTable() {
        tableView.getColumns().clear();

        TableColumn<Object, String> c1 = new TableColumn<>("Customer ID");
        TableColumn<Object, String> c2 = new TableColumn<>("Name");
        TableColumn<Object, String> c3 = new TableColumn<>("Email");
        TableColumn<Object, String> c4 = new TableColumn<>("Phone");

        tableView.getColumns().addAll(c1, c2, c3, c4);

        field1Input.setPromptText("ID");
        field2Input.setPromptText("Name");
        field3Input.setPromptText("Email");
        field4Input.setPromptText("Phone");

        data.clear();
    }

    private void refreshTable() {
        switch (active) {
            case PRODUCTS -> data.setAll(productRepository.findAll());
            default -> data.clear();
        }
        tableView.setItems(data);
    }

    @FXML
    public void selectProducts() {
        active = ActiveTable.PRODUCTS;
        loadProductsTable();
        refreshTable();
    }

    @FXML
    public void selectOrders() {
        active = ActiveTable.ORDERS;
        loadOrdersTable();
        refreshTable();
    }

    @FXML
    public void selectSales() {
        active = ActiveTable.SALES;
        loadSalesTable();
        refreshTable();
    }

    @FXML
    public void selectCustomers() {
        active = ActiveTable.CUSTOMERS;
        loadCustomersTable();
        refreshTable();
    }

    @FXML
    public void onAdd() {
        if (active != ActiveTable.PRODUCTS) {
            new Alert(Alert.AlertType.WARNING,
                    "Adding is only available for Products right now.").showAndWait();
            return;
        }

        try {
            Product p = Product.builder()
                    .name(field1Input.getText())
                    .category(field2Input.getText())
                    .price(Double.parseDouble(field3Input.getText()))
                    .stock(Integer.parseInt(field4Input.getText()))
                    .build();

            productRepository.save(p);
            refreshTable();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Invalid numeric values!").showAndWait();
        }
    }

    @FXML
    public void onRemove() {
        if (active != ActiveTable.PRODUCTS) {
            new Alert(Alert.AlertType.WARNING,
                    "Removing is only available for Products right now.").showAndWait();
            return;
        }

        Product selected = (Product) tableView.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        productRepository.delete(selected);
        refreshTable();
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
        if (active != ActiveTable.PRODUCTS) {
            new Alert(Alert.AlertType.INFORMATION,
                    "Search works only for Products at this stage.").showAndWait();
            return;
        }

        String text = searchField.getText().toLowerCase().trim();

        if (text.isBlank()) {
            refreshTable();
            return;
        }

        data.setAll(
                productRepository.findAll().stream()
                        .filter(p ->
                                p.getName().toLowerCase().contains(text) ||
                                        p.getCategory().toLowerCase().contains(text) ||
                                        String.valueOf(p.getPrice()).contains(text) ||
                                        String.valueOf(p.getStock()).contains(text)
                        )
                        .toList()
        );
    }
}
