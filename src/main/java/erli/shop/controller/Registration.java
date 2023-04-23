package erli.shop.controller;

import erli.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Registration {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/auth")
    public String auth(){
           return null;
    }
}
