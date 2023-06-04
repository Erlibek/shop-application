package erli.shop.controller;

import erli.shop.service.ProductService;
import erli.shop.service.ReviewService;
import erli.shop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    private final UserService userService;
    private final ReviewService reviewService;

    @GetMapping(path = "/moderation/products")
    public String products(
            Model model
    ) {
        model.addAttribute("products", productService.productList());
        model.addAttribute("price", productService.sumProduct());
        model.addAttribute("users", userService.getCurrentUser());
        return "product/product_list_page";
    }

    @GetMapping(path = "/moderation/categories")
    public String categories(
            @RequestParam long categoryId,
            Model model
    ) {
        model.addAttribute("categories", productService.categoryList(categoryId));
        model.addAttribute("price", productService.sumProductCategory(categoryId));
        model.addAttribute("users", userService.getCurrentUser());
        return "product/category_page";
    }

    @GetMapping(path = "/moderation/create")
    public String createProductPage(@RequestParam(required = false) Long categoryId,
                                    Model model) {
        if (categoryId != null) {
            model.addAttribute("category", productService.categoryList(categoryId));
        } else {
            model.addAttribute("categories", productService.categories());
        }
        model.addAttribute("categoryId", categoryId);
        return "product/create_product_page";
    }

    @PostMapping(path = "/moderation/create_product")
    public String createProductAction(
            @RequestParam Long categoryId,
            @RequestParam String name,
            @RequestParam int price,
            @RequestParam(name = "option") List<Long> optionIds,
            @RequestParam(name = "value") List<String> values
    ) {
        productService.createProduct(categoryId, name, price, optionIds, values);
        return "redirect:/moderation/products";
    }

    @GetMapping(path = "/moderation/update_product")
    public String updateProductPage(
            @RequestParam(required = false) Long productId,
            Model model
    ) {
        ;
        model.addAttribute("product", productService.oneProduct(productId));
        model.addAttribute("productId", productId);
        return "product/update_product_page";
    }

    @PostMapping(path = "/moderation/update_product")
    public String updateProductPage(
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) int price,
            @RequestParam(required = false, name = "option") List<Long> optionIds,
            @RequestParam(required = false, name = "value") List<String> values,
            Model model
    ) {
        productService.updateProduct(productId, name, price, optionIds, values);
        return "redirect:/moderation/products";
    }

    @GetMapping(path = "/moderation/delete_product")
    public String deleteProduct(@RequestParam Long productId) {
        productService.deleteProduct(productId);
        return "redirect:/moderation/products";
    }

    @GetMapping(path = "/moderation/product_page")
    public String moreByProduct(
            @RequestParam Long productId,
            Model model
    ) {
        model.addAttribute("product", productService.product(productId));
        model.addAttribute("total", productService.statusRating(productId));
        model.addAttribute("moderators", reviewService.reviewList(productId));
        model.addAttribute("result", productService.banRepeatedComments(productId));
        return "product/product_page";
    }
}