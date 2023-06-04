package erli.shop.controller;

import erli.shop.service.OrderService;
import erli.shop.service.ReviewService;
import erli.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class PersonalCabinetController {
   private final ReviewService reviewService;
    private final UserService userService;
    private final OrderService orderService;

    @GetMapping(path = "/profile")
    public String cabinetPage(Model model) {
        model.addAttribute("users", userService.getCurrentUser());
        model.addAttribute("orders", orderService.orderListForCabinet());
        model.addAttribute("comments", reviewService.reviewListForCabinet());
        return "/cabinet/personal_profile";
    }

    @GetMapping(path = "/profile/orders/{orderId}")
    public String orderProduct(@PathVariable Long orderId, Model model) {
        model.addAttribute("sumOrders", orderService.sumOneOrder(orderId));
        model.addAttribute("order", orderService.oneOrder(orderId));
        return "/cabinet/orderProduct_page";
    }
}