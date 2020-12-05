package ru.pavel_zhukoff.car_rent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pavel_zhukoff.car_rent.repository.CarBrandRepository;

@Controller
@RequestMapping("/")
public class HomeController {


    @GetMapping("")
    public String home(Model model) {
        model.addAttribute("title", "Админпанель");
        return "home";
    }
}
