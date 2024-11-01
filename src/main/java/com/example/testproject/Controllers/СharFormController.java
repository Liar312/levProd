package com.example.testproject.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class СharFormController {
    @GetMapping("/create/char")
    public String showCreateCharForm(){
        return "card";
    }
}
