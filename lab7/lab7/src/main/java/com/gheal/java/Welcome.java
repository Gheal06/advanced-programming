package com.gheal.java;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Controller 
public class Welcome {
    @GetMapping("/welcome")
    public String welcome(Model model) {
        model.addAttribute("message", "Hello from Spring Boot!");
        return "welcome"; // The name of a template
    }
}