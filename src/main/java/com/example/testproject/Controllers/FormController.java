package com.example.testproject.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class FormController {
    @GetMapping("/reg")
    public String showRegistrationForm(){
        return "reg";
    }


}
