package erli.shop.repository;

import erli.shop.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByProductIdAndPublished(long productId, boolean published);

    Review findById(long reviewId);
    List<Review> findAllByPublished(boolean published);

    List<Review> findAllByUserId(long userId);

    Review findByProductIdAndUserId(long productId, long userId);
}
