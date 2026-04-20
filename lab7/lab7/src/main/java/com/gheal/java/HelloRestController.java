package com.gheal.java;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
public class HelloRestController {
    @GetMapping("/hello")
    public String sayHello(@RequestParam("name") String name) {
        return String.format("Hello, %s!", name);
    }
}