package erli.shop.controller;

import erli.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(path = "/login")
    public String authorization() {
        return "user/user_auth";
    }

    @GetMapping(path = "/user/registration")
    public String registration() {
        return "user/user_registration";
    }

    @PostMapping(path = "/user/registration")
    public String registration(
            @RequestParam String name,
            @RequestParam String lastName,
            @RequestParam String login,
            @RequestParam String password
    ) {
        userService.createUser(name, lastName, login, password);
        return "redirect:/login";
    }
}