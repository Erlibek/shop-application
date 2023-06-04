package erli.shop.controller;

import erli.shop.entity.Order;
import erli.shop.entity.enumeration.Status;
import erli.shop.service.OrderService;
import erli.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;


@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping(path = "/order_resource")
    public String orderPage(
            @RequestParam String address
    ) {
        orderService.createOrder(address);
        return "redirect:/cart/cart_page";
    }
}