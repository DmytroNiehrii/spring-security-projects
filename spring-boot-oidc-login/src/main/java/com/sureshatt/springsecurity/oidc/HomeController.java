package com.sureshatt.springsecurity.oidc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/")
    public String welcomeView(Model model, Principal principal) {
        model.addAttribute("sub", principal.getName());
        return "home";
    }
}
