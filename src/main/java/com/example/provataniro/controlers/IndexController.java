package com.example.provataniro.controlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping(value = {"/home"})
    public String Homeapp() {
        return "index";
    }
}
