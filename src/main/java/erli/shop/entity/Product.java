package erli.shop.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity // Нам нужна чтобы hibernate воспринимал наш класс как сущность
@Table(name = "products") // нам нужна чтобы сушность была подвязана какой то таблице
public class Product {
    @Id // служит для обозначения первичного ключа
    @GeneratedValue(strategy = GenerationType.IDENTITY)// генерация значения поля переводится на базу данных, hibernate за это не отвечает
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    //поле внешнего ключа в таблице, через которое строится связь на категорию
    //сейчас мы описали как объект продукта относится к объекту категория
    // и то же самое надо сделать с категорией
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<Value> values;

    private String name;

    private Integer price;

    @OneToMany(mappedBy = "product")
    private List<OrderProduct> orderProducts;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
