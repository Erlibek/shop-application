package erli.shop.repository;

import erli.shop.entity.Order;
import erli.shop.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct,Long> {


}
