package edu.eci.cvds.user_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerRedirectController {

    @GetMapping("/doc")
    public String redirectToSwaggerUI() {
        return "redirect:/swagger-ui.html";
    }
}
