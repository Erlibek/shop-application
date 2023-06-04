package erli.shop.service;

import erli.shop.entity.Product;
import erli.shop.entity.Review;
import erli.shop.repository.ProductRepository;
import erli.shop.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final UserService userService;

    private final ProductRepository productRepository;


    public void addNewComment( Long productId, Integer rating, String text) {
        Review review = new Review();
        review.setUser(userService.getCurrentUser());
        review.setProduct(productRepository.findById(productId).orElseThrow());
        review.setPublished(false);
        review.setRating(rating);
        review.setText(text);
        review.setDate(LocalDate.now());
        reviewRepository.save(review);
    }

    public List<Review> reviews() {
        List<Review> reviews = reviewRepository.findAllByPublished(true);
        return reviews;
    }

    public List<Review> reviewList(Long productId) {
        List<Review> reviewList = reviewRepository.findAllByProductIdAndPublished(productId, true);

        return reviewList;
    }

    public List<Review> reviewListModerator() {
        List<Review> reviewList = reviewRepository.findAllByPublished(false);
        return reviewList;
    }

    public List<Review> reviewsAll() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews;
    }
    public void saveModerationNewComment(Long commentId, boolean moderation) {
        Review review = reviewRepository.findById(commentId).orElseThrow();
        if (!moderation) {
            reviewRepository.delete(review);
        }else {
            review.setPublished(moderation);
            reviewRepository.save(review);
        }
    }

    public void deleteReview(Long commentId) {
        Review review = reviewRepository.findById(commentId).orElseThrow();
        reviewRepository.delete(review);
    }

    public List<Review> reviewListForCabinet(){
        List<Review> reviewList = reviewRepository.findAllByUserId(userService.getCurrentUser().getId());
        return reviewList;
    }
}
