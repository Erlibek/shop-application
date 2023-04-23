package erli.shop.repository;

import erli.shop.entity.Category;
import erli.shop.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionRepository extends JpaRepository<Option, Long> {
    List<Option> findAllByCategoryOrderById(Category category);
}
