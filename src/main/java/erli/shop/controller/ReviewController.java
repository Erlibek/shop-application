package erli.shop.controller;

import erli.shop.entity.Review;
import erli.shop.service.ProductService;
import erli.shop.service.ReviewService;
import erli.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final UserService userService;


    @GetMapping(path = "/reviews")
    public String reviewPage() {
        return "/product/product_page";
    }


    @PostMapping(path = "reviews/create")
    public String addComment(
            @RequestParam(required = true) Long productId,
            @RequestParam(required = true) Integer rating,
            @RequestParam(required = true) String text
    ) {
        reviewService.addNewComment(productId,rating,text);
        return String.format("redirect:/products/product_page?productId=%d", productId);
    }
}