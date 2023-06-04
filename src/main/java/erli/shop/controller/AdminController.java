package erli.shop.controller;

import erli.shop.entity.enumeration.Status;
import erli.shop.service.OrderService;
import erli.shop.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/admin")
public class AdminController {
    private final OrderService orderService;
    private final ReviewService reviewService;

    @GetMapping(path = "/orders")
    public String changeRole(Model model) {
        model.addAttribute("statuses", Status.values());
        model.addAttribute("orders", orderService.orderList());
        return "admin/admin_list";
    }

    @PostMapping(path = "/orders/change_order_status")
    public String changeStatus(@RequestParam Status status, Long orderId) {
        orderService.changeStatus(status, orderId);
        return "redirect:/admin/orders";
    }


    @GetMapping(path = "/reviews")
    public String moderatorPage(Model model) {
        model.addAttribute("moderation", reviewService.reviewListModerator());
        model.addAttribute("reviewList", reviewService.reviewsAll());
        return "moderator/moderator_list";
    }

    @GetMapping(path = "/reviews/add")
    public String moderatorComment(
            @RequestParam(required = true) Long commentId,
            @RequestParam(required = true) boolean moderation
    ) {
       reviewService.saveModerationNewComment(commentId, moderation);
        return "redirect:/admin/reviews";
    }

    @GetMapping(path = "/reviews/delete")
    public String moderatorComments(
            @RequestParam(required = true) Long commentId
    ) {
        reviewService.deleteReview(commentId);
        return "redirect:/admin/reviews";
    }
}