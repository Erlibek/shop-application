package erli.shop.controller;

import erli.shop.service.CartService;
import erli.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/cart")
public class CartController {

    private final CartService cartService;

    private final UserService userService;

    @GetMapping(path = "/cart_page")
    public String cartPage(Model model) {
        model.addAttribute("cartItems", cartService.cartItemList());
        model.addAttribute("total", cartService.sumAllProductCart());
        return "cart/cart_page";
    }

    @GetMapping(path = "/add_product_cart")
    public String addProductToCart(@RequestParam long productId,
                                   Model model) {
        cartService.addProductToCart(productId);
        return "redirect:/products";
    }

    @GetMapping(path = "/increase")
    public String increaseProductCount(@RequestParam long productId) {
        cartService.increaseProduct(productId);
        return "redirect:/cart/cart_page";
    }


    @GetMapping(path = "/decrease")
    public String decreaseProductCount(@RequestParam long productId) {
        cartService.decreaseProduct(productId);
        return "redirect:/cart/cart_page";
    }

    @GetMapping(path = "/delete")
    public String deleteProduct(@RequestParam Long productId) {
        cartService.deleteProductFromCart(productId);
        return "redirect:/cart/cart_page";
    }
}