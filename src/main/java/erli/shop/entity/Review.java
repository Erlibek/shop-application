package erli.shop.entity;

import erli.shop.entity.Product;
import erli.shop.entity.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "is_published")
    private Boolean published;

    private Integer rating;

    private String text;

    @Column(name = "created_date")
    private LocalDateTime date;
}
