package erli.shop.repository;


import erli.shop.entity.CartItem;
import erli.shop.entity.Product;
import erli.shop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Boolean existsCartByUserAndProduct(User user, Product product);

    List<CartItem> findAllByUserIdOrderByProductId(long userId);

    CartItem findByProductIdAndUserIdOrderByProductId(long productId, long userId);

    void deleteByUser(User user);
}
