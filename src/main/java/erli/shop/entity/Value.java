package erli.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity//нам нужна чтобы хибернейт воспринимал наш класс как сущность
@Table(name = "values")//нам нужна чтобы сущность была подвязана какой то таблице
public class Value {
    @Id//служит для обозначения первичного ключа
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//генерация значения поля переводится на базу данных, хибернейт за это не отвечает
    private Long id;

    @ManyToOne
    @JoinColumn(name = "option_id")
    //поле внешнего ключа в таблице, через которое строится связь на option
    private Option option;

    @ManyToOne
    @JoinColumn(name = "product_id")
    //поле внешнего ключа в таблице, через которое строится связь на value
    private Product product;

    private String value;
}
