package ru.pavel_zhukoff.car_rent.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.pavel_zhukoff.car_rent.dto.ColorForm;
import ru.pavel_zhukoff.car_rent.model.CarColor;
import ru.pavel_zhukoff.car_rent.service.CarColorService;

import javax.validation.Valid;
import java.util.Optional;

@RequestMapping("/color")
@Controller
@Slf4j
public class CarColorController {
    
    private final CarColorService colorService;

    public CarColorController(CarColorService colorService) {
        this.colorService = colorService;
    }

    @GetMapping("")
    public String index(Model model) {
        log.info("GET /color");
        model.addAttribute("title", "Список цветов");
        model.addAttribute("items", this.colorService.findAllActive());
        model.addAttribute("entity_type", "color");
        model.addAttribute("list_name", "Цвета автомобилей");
        return "list_view.html";
    }

    @GetMapping("/all")
    public String listAll(Model model) {
        log.info("GET /color/all");
        model.addAttribute("title", "Полный список цветов");
        model.addAttribute("items", this.colorService.findAll());
        model.addAttribute("entity_type", "color");
        model.addAttribute("list_name", "Цвета автомобилей");
        return "list_view.html";
    }

    @GetMapping("/add")
    public String add(Model model) {
        log.info("GET /color/add");
        model.addAttribute("title", "Новый цвет");
        model.addAttribute("action", "add");
        model.addAttribute("form", new ColorForm());
        model.addAttribute("entity_type", "color");
        model.addAttribute("form_name", "Новый цвет");
        return "name_form.html";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("form") ColorForm form, BindingResult result, Model model) {
        log.info("POST /color/add");
        String name = StringUtils.capitalize(form.getName().strip());
        Optional<CarColor> color = this.colorService.findByName(name);
        if (color.isPresent()) {
            if (color.get().isActive()) {
                result.addError(new ObjectError("name", "Этот цвет уже в базе данных!"));
            } else {
                color.get().setActive(true);
                this.colorService.save(color.get());
                return "redirect:/color";
            }
        }
        if (result.hasErrors()) {
            model.addAttribute("title", "Новый цвет");
            model.addAttribute("action", "add");
            model.addAttribute("form", form);
            model.addAttribute("entity_type", "color");
            model.addAttribute("form_name", "Новый цвет");
            return "name_form.html";
        }
        CarColor newColor = new CarColor();
        newColor.setName(name);
        newColor.setActive(true);
        this.colorService.save(newColor);
        return "redirect:/color";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        log.info(String.format("GET /color/update/%d", id));
        Optional<CarColor> color = this.colorService.findById(id);
        if (color.isEmpty()) {
            return "redirect:/color";
        }
        ColorForm form = new ColorForm();
        form.setName(color.get().getName());
        form.setActive(color.get().isActive());
        model.addAttribute("title", String.format("Изменение: %s", color.get().getName()));
        model.addAttribute("action", String.format("update/%d", color.get().getId()));
        model.addAttribute("form", form);
        model.addAttribute("entity_type", "color");
        model.addAttribute("form_name", "Обновление цвета");

        return "name_form.html";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("form") ColorForm form,
                         BindingResult result,
                         Model model) {
        log.info(String.format("POST /color/update/%d", id));
        Optional<CarColor> color = this.colorService.findById(id);
        if (color.isEmpty()) {
            return "redirect:/color";
        }
        if (result.hasErrors()) {
            model.addAttribute("title", String.format("Изменение: %s", id));
            model.addAttribute("action", String.format("update/%d", id));
            model.addAttribute("form", form);
            model.addAttribute("entity_type", "color");
            model.addAttribute("form_name", "Обновление цвета");
            return "color/name_form.html";
        }
        CarColor u = new CarColor();
        u.setId(id);
        u.setName(form.getName());
        u.setActive(form.isActive());
        this.colorService.update(u);
        return "redirect:/color";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        this.colorService.deleteById(id);
        return "redirect:/color";
    }
}
