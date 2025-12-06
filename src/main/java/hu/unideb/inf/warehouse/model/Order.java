package hu.unideb.inf.warehouse.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders") // mert 'order' SQL kulcsszó
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    private LocalDateTime orderDate;

    private String status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Sale> sales;
}
