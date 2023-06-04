package erli.shop.entity;

import erli.shop.entity.enumeration.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated
    private Status status;

    private String address;

    @Column(name = "created_date")
    private LocalDate date;

    @OneToMany(mappedBy = "order")
    private List<OrderProduct> orderProducts;
}
