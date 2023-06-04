package erli.shop.controller;

import erli.shop.service.ProductService;
import erli.shop.service.ReviewService;
import erli.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ProductUserController {
    private final ProductService productService;
    private final UserService userService;
    private final ReviewService reviewService;

    @GetMapping(path = "/products")
    public String products(
            Model model
    ) {
        model.addAttribute("products", productService.productList());
        model.addAttribute("price", productService.sumProduct());
        model.addAttribute("users", userService.getCurrentUser());
        return "product/product_user_page";
    }

    @GetMapping(path = "/products/categories")
    public String categories(
            @RequestParam long categoryId,
            Model model
    ) {
        model.addAttribute("categories", productService.categoryList(categoryId));
        model.addAttribute("price", productService.sumProductCategory(categoryId));
        model.addAttribute("users", userService.getCurrentUser());
        return "product/category_user_page";
    }

    @GetMapping(path = "/products/product_page")
    public String moreByProduct(
            @RequestParam Long productId,
            Model model
    ) {
        model.addAttribute("product", productService.product(productId));
        model.addAttribute("total", productService.statusRating(productId));
        model.addAttribute("moderators", reviewService.reviewList(productId));
        model.addAttribute("result", productService.banRepeatedComments(productId));
        model.addAttribute("users", userService.getCurrentUser());
        return "product/product_page";
    }
}