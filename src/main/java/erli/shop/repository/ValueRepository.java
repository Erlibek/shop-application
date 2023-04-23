package erli.shop.repository;

import erli.shop.entity.Option;
import erli.shop.entity.Product;
import erli.shop.entity.Value;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ValueRepository extends JpaRepository<Value, Long> {
    Value findByProductAndOption(Product product, Option option);
}
