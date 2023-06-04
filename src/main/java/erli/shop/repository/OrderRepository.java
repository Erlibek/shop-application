package erli.shop.repository;

import erli.shop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {


    List<Order> findAllById(Long orderId);
    List<Order> findAllByUserId(long userId);

    Order findByUserId(long userId);
}
