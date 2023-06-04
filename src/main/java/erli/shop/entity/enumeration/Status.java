package erli.shop.entity.enumeration;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum Status {
    INSTOCK("На складе"),
    DELIVERY("Доставка"),
    DELIEVERED("Доставлен");
    private final String displayStatusName;

    Status(String displayStatusName){
        this.displayStatusName = displayStatusName;
    }
}
