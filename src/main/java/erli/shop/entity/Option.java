package erli.shop.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity // нам нужна, чтобы хибернейт воспринимал наш класс, как сущность
@Table(name = "options") // нам нужна чтобы наша  сущность была подвязана какой то таблице
public class Option {
    @Id//служит для обозначения первичного ключа
    @GeneratedValue(strategy = GenerationType.IDENTITY)//генерация значения поля переводится на базу данных, хибернейт за это не отвечает
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    //поле внешнего ключа в таблице, через которое строится связь на категорию
    private Category category;

    @OneToMany(mappedBy = "option")
    private List<Value> values;

    public List<Value> getValues() {
        return values;
    }

    public void setValues(List<Value> values) {
        this.values = values;
    }

    private String name;

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
}
