package hu.unideb.inf.warehouse.frontend.controller;

import hu.unideb.inf.warehouse.model.Customer;
import hu.unideb.inf.warehouse.model.Product;
import hu.unideb.inf.warehouse.model.Order;
import hu.unideb.inf.warehouse.model.Sale;
import hu.unideb.inf.warehouse.repository.CustomerRepository;
import hu.unideb.inf.warehouse.repository.OrderRepository;
import hu.unideb.inf.warehouse.repository.ProductRepository;
import hu.unideb.inf.warehouse.repository.SaleRepository;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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
    @FXML private TextField field5Input;

    @FXML private TextField searchField;

    @FXML private Label statusLabel;

    private ObservableList<Object> data = FXCollections.observableArrayList();
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private SaleRepository saleRepository;

    private enum ActiveTable { PRODUCTS, ORDERS, SALES, CUSTOMERS }
    private ActiveTable active = ActiveTable.PRODUCTS;

    @FXML
    public void initialize() {
        loadProductsTable();
        refreshTable();
        setStatus("Ready");
    }

    private void loadProductsTable() {
        tableView.getColumns().clear();

        TableColumn<Object, String> c0 = new TableColumn<>("ID");
        c0.setCellValueFactory(param ->
               new SimpleStringProperty(String.valueOf(((Product) param.getValue()).getId())));

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

        tableView.getColumns().addAll(c0, c1, c2, c3, c4);


        field1Input.setPromptText("Name");
        field2Input.setPromptText("Category");
        field3Input.setPromptText("Price");
        field4Input.setPromptText("Stock");

        // nincs 5. input
        field5Input.setPromptText("");
        field5Input.setDisable(true);
    }

    private void loadOrdersTable() {
        tableView.getColumns().clear();

        TableColumn<Object, String> c1 = new TableColumn<>("Order ID");
        c1.setCellValueFactory(param ->
                new SimpleStringProperty(String.valueOf(((Order) param.getValue()).getId())));
        TableColumn<Object, String> c2 = new TableColumn<>("Customer ID");
        c2.setCellValueFactory(param ->
                new SimpleStringProperty(((Order) param.getValue()).getCustomer().getName()));
        TableColumn<Object, String> c3 = new TableColumn<>("Date");
        c3.setCellValueFactory(param ->
                new SimpleStringProperty(String.valueOf(((Order) param.getValue()).getOrderDate())));
        TableColumn<Object, String> c4 = new TableColumn<>("Status");
        c4.setCellValueFactory(param ->
                new SimpleStringProperty(((Order) param.getValue()).getStatus()));

        tableView.getColumns().addAll(c1, c2, c3, c4);

        field1Input.setPromptText("Customer ID");
        field2Input.setPromptText("Order Date");
        field3Input.setPromptText("Status");

        // nincs 4-5. input
        field4Input.setPromptText("");
        field4Input.setDisable(true);

        field5Input.setPromptText("");
        field5Input.setDisable(true);

        data.clear();
    }

    private void loadSalesTable() {
        tableView.getColumns().clear();

        TableColumn<Object, String> c1 = new TableColumn<>("Sale ID");
        c1.setCellValueFactory(param ->
                new SimpleStringProperty(String.valueOf(((Sale) param.getValue()).getId())));
        TableColumn<Object, String> c2 = new TableColumn<>("Product ID");
        c2.setCellValueFactory(param ->
                new SimpleStringProperty(((Sale) param.getValue()).getProduct().getName()));
        TableColumn<Object, String> c3 = new TableColumn<>("Order ID");
        c3.setCellValueFactory(param ->
                new SimpleStringProperty(String.valueOf(((Sale) param.getValue()).getOrder().getId())));
        TableColumn<Object, String> c4 = new TableColumn<>("Quantity");
        c4.setCellValueFactory(param ->
                new SimpleStringProperty(String.valueOf(((Sale) param.getValue()).getQuantity())));
        TableColumn<Object, String> c5 = new TableColumn<>("Total Price");
        c5.setCellValueFactory(param ->{
            Sale sale = (Sale) param.getValue();
            double price = sale.getProduct().getPrice();
            int quantity = sale.getQuantity();
            double total =  price * quantity;
            return  new SimpleStringProperty(String.format("%.3f", total));

        });
        tableView.getColumns().addAll(c1, c2, c3, c4,c5);

        field1Input.setPromptText("Order ID");
        field2Input.setPromptText("Product ID");
        field3Input.setPromptText("Quantity");

        // nincs 4-5. input
        field4Input.setPromptText("");
        field4Input.setDisable(true);

        field5Input.setPromptText("");
        field5Input.setDisable(true);

        data.clear();
    }

    private void loadCustomersTable() {
        tableView.getColumns().clear();

        TableColumn<Object, String> c1 = new TableColumn<>("Customer ID");
        c1.setCellValueFactory(param ->
                new SimpleStringProperty(String.valueOf(((Customer) param.getValue()).getId())));
        TableColumn<Object, String> c2 = new TableColumn<>("Name");
        c2.setCellValueFactory(param ->
                new SimpleStringProperty(((Customer) param.getValue()).getName()));
        TableColumn<Object, String> c3 = new TableColumn<>("Email");
        c3.setCellValueFactory(param ->
                new SimpleStringProperty(((Customer) param.getValue()).getEmail()));
        TableColumn<Object, String> c4 = new TableColumn<>("Phone");
        c4.setCellValueFactory(param ->
                new SimpleStringProperty(((Customer) param.getValue()).getPhone()));

        tableView.getColumns().addAll(c1, c2, c3, c4);

        field1Input.setPromptText("Name");
        field2Input.setPromptText("Email");
        field3Input.setPromptText("Phone");

        // nincs 4-5. input
        field4Input.setPromptText("");
        field4Input.setDisable(true);

        field5Input.setPromptText("");
        field5Input.setDisable(true);

        data.clear();
    }

    private void refreshTable() {
        switch (active) {
            case PRODUCTS -> data.setAll(productRepository.findAll());
            case CUSTOMERS -> data.setAll(customerRepository.findAll());
            case ORDERS -> data.setAll(orderRepository.findAll());
            case SALES -> data.setAll(saleRepository.findAll());
            default -> data.clear();
        }
        tableView.setItems(data);
    }

    @FXML
    public void selectProducts() {
        active = ActiveTable.PRODUCTS;
        loadProductsTable();
        refreshTable();
        onClear();
        setStatus("Viewing products.");

    }

    @FXML
    public void selectOrders() {
        active = ActiveTable.ORDERS;
        loadOrdersTable();
        refreshTable();
        onClear();
        setStatus("Viewing orders.");
    }

    @FXML
    public void selectSales() {
        active = ActiveTable.SALES;
        loadSalesTable();
        refreshTable();
        onClear();
        setStatus("Viewing sales.");
    }

    @FXML
    public void selectCustomers() {
        active = ActiveTable.CUSTOMERS;
        loadCustomersTable();
        refreshTable();
        onClear();
        setStatus("Viewing customers.");
    }

    @FXML
    public void onAdd() {
        try {
            switch (active) {
                case PRODUCTS -> {
                    Product p = Product.builder()
                            .name(field1Input.getText())
                            .category(field2Input.getText())
                            .price(Double.parseDouble(field3Input.getText()))
                            .stock(Integer.parseInt(field4Input.getText()))
                            .build();
                    productRepository.save(p);
                    setStatus("Product added successfully.");
                }


                case CUSTOMERS -> {
                    Customer c = Customer.builder()
                            .name(field1Input.getText())
                            .email(field2Input.getText())
                            .phone(field3Input.getText())
                            .build();
                    customerRepository.save(c);
                    setStatus("Customer added successfully.");
                }


                case ORDERS -> {
                    Long customerId = Long.parseLong(field1Input.getText());
                    Customer customer = customerRepository.findById(customerId).orElse(null);

                    Order o = Order.builder()
                            .customer(customer)
                            .orderDate(LocalDateTime.parse(field2Input.getText()))
                            .status(field3Input.getText())
                            .build();

                    orderRepository.save(o);
                    setStatus("Order added successfully.");
                }


                case SALES -> {
                    Long orderId = Long.parseLong(field1Input.getText());
                    Order order = orderRepository.findById(orderId).orElse(null);

                    if (order == null) {
                        throw new IllegalArgumentException("Order not found");
                    }

                    Long productId = Long.parseLong(field2Input.getText());
                    Product product = productRepository.findById(productId).orElse(null);

                    if (product == null) {
                        throw new IllegalArgumentException("Product not found");
                    }

                    double total = Integer.parseInt(field3Input.getText()) *  product.getPrice();

                    Sale s = Sale.builder()
                            .order(order)
                            .product(product)
                            .quantity(Integer.parseInt(field3Input.getText()))
                            .total(total)
                            .build();
                    saleRepository.save(s);
                    setStatus("Sale added successfully.");

                }
            }
            refreshTable();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Invalid numeric values!").showAndWait();
            setStatus("Failed to add item: invalid numeric values.");
        }
    }

    @FXML
    public void onRemove() {
        Object selected = tableView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            new Alert(Alert.AlertType.ERROR, "Invalid selection!").showAndWait();
            setStatus("Remove failed: no item selected.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this item?", ButtonType.YES, ButtonType.NO);
        confirm.setHeaderText(null);
        confirm.showAndWait();

        if (confirm.getResult() != ButtonType.YES) {
            setStatus("Remove cancelled.");
            return;
        }

        switch (active) {
            case PRODUCTS -> productRepository.delete((Product) selected);
            case CUSTOMERS -> customerRepository.delete((Customer) selected);
            case ORDERS -> orderRepository.delete((Order) selected);
            case SALES -> saleRepository.delete((Sale) selected);
        }

        refreshTable();
        setStatus("Item removed successfully.");
    }

    @FXML
    public void onClear() {
        field1Input.clear();
        field2Input.clear();
        field3Input.clear();
        field4Input.clear();
        field5Input.clear();
    }

    @FXML
    public void onSearch() {
        if (active != ActiveTable.PRODUCTS) {
            new Alert(Alert.AlertType.INFORMATION,
                    "Search works only for Products at this stage.").showAndWait();
            setStatus("Search is only available for products.");
            return;
        }

        String text = searchField.getText().toLowerCase().trim();

        if (text.isBlank()) {
            refreshTable();
            setStatus("Search cleared, showing all products.");
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

        setStatus("Search completed: " + data.size() + " product(s) found.");
    }

    private void setStatus(String message) {
        if (statusLabel != null) {
            statusLabel.setText(message);
        }
    }
}
