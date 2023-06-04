package erli.shop.service;

import erli.shop.entity.*;
import erli.shop.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ValueRepository valueRepository;

    private final OptionRepository optionRepository;

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;


    private final ReviewService reviewService;

    private final UserService userService;

    private final ReviewRepository reviewRepository;

    private final EntityManager entityManager;

    public void createProduct(
            long categoryId,
            String name,
            Integer price,
            List<Long> option,
            List<String> values
    ) {
        Product product = new Product();
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        List<Option> options = optionRepository.findAllByCategoryOrderById(category);
        product.setCategory(category);
        product.setName(name);
        product.setPrice(price);
        productRepository.save(product);
        for (int i = 0; i < option.size(); i++) {
            Value value = new Value();
            value.setValue(values.get(i));
            value.setOption(options.get(i));
            value.setProduct(product);
            valueRepository.save(value);
        }
    }

    public void updateProduct(Long productId, String name, int price, List<Long> optionIds, List<String> values) {
        Product product = productRepository.findById(productId).orElseThrow();
        if (name != null) product.setName(name);
        if (price != 0) product.setPrice(price);

        List<Option> options = optionRepository.findAllByCategoryOrderById(product.getCategory());
        for (int i = 0; i < options.size(); i++) {
            Value value = valueRepository.findByProductAndOption(product, options.get(i));
            if (value == null) {
                value = new Value();
                value.setProduct(product);
                value.setOption(options.get(i));
                value.setValue(values.get(i));
            }
            value.setProduct(product);
            value.setOption(options.get(i));
            value.setValue(values.get(i));
            valueRepository.save(value);
        }
        productRepository.save(product);
    }

    public void deleteProduct(long productId) {
        Product product = productRepository.findById(productId).orElseThrow();
        List<Value> values = product.getValues();
        for (Value value : values) {
            valueRepository.deleteById(value.getId());
        }
        productRepository.deleteById(productId);
    }

    public Product product(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow();
        return product;
    }

    public double statusRating(Long productId) {
        double averageRating = 0;

        if (reviewService.reviewList(productId).size() != 0) {
            for (Review review : reviewService.reviewList(productId)) {
                averageRating += review.getRating() / reviewService.reviewList(productId).size();
            }
        }
        return averageRating;
    }

    public boolean banRepeatedComments(long productId) {
        boolean result;
        if (userService.getCurrentUser() != null) {
            Review review = reviewRepository.findByProductIdAndUserId(productId, userService.getCurrentUser().getId());
            result = true;
            if (review != null) {
                result = false;
            } else {
                result = true;
            }
        } else {
            result = false;
        }
        return result;
    }

    public List<Product> productList() {
        List<Product> product = productRepository.findAll();
        return product;
    }

    public int sumProduct() {
        int sum = 0;
        List<Product> products = productRepository.findAll();
        for (Product product:products) {
            sum += product.getPrice();
        }
        return sum;
    }

    public Category categoryList(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow();
    }

    public int sumProductCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        int sum = 0;
        for (Product product:category.getProducts()) {
            sum += product.getPrice();
        }
        return sum;
    }

    public List<Category> categories() {
        return categoryRepository.findAll();
    }

    public Product oneProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow();
    }
}

