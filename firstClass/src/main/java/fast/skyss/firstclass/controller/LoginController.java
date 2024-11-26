package fast.skyss.firstclass.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/outtime")
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "outtime/login";
    }

    @PostMapping("/login")
    public String handleLogin() {
        return "redirect:/outtime/home";
    }

    @GetMapping("/home")
    public String home() {
        return "outtime/home";
    }

    @GetMapping("/register")
    public String register() {
        return "outtime/register";
    }
}
