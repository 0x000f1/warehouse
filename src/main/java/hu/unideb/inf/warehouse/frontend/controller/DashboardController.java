package hu.unideb.inf.warehouse.frontend.controller;

import hu.unideb.inf.warehouse.repository.CustomerRepository;
import hu.unideb.inf.warehouse.repository.OrderRepository;
import hu.unideb.inf.warehouse.repository.ProductRepository;
import hu.unideb.inf.warehouse.repository.SaleRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.stereotype.Component;

@Component
public class DashboardController {

    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final SaleRepository saleRepository;

    @FXML private Label totalProductsLabel;
    @FXML private Label totalCustomersLabel;
    @FXML private Label totalOrdersLabel;
    @FXML private Label totalRevenueLabel;

    public DashboardController(ProductRepository productRepository,
                               CustomerRepository customerRepository,
                               OrderRepository orderRepository,
                               SaleRepository saleRepository) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.saleRepository = saleRepository;
    }

    @FXML
    public void initialize() {
        refreshStats();
    }

    private void refreshStats() {
        long products = productRepository.count();
        long customers = customerRepository.count();
        long orders = orderRepository.count();

        double revenue = saleRepository.findAll().stream()
                .mapToDouble(s -> (s.getTotal() != null ? s.getTotal() : 0.0))
                .sum();

        totalProductsLabel.setText(String.valueOf(products));
        totalCustomersLabel.setText(String.valueOf(customers));
        totalOrdersLabel.setText(String.valueOf(orders));
        totalRevenueLabel.setText(String.format("%.2f", revenue));
    }
}
