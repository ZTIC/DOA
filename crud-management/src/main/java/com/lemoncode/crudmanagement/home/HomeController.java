package com.lemoncode.crudmanagement.home;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/home")
public class HomeController {

    public String homeController() {
        return "index";
    }
}
