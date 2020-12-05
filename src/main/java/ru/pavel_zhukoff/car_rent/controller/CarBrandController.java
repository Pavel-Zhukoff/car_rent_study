package ru.pavel_zhukoff.car_rent.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.pavel_zhukoff.car_rent.dto.BrandForm;
import ru.pavel_zhukoff.car_rent.model.CarBrand;
import ru.pavel_zhukoff.car_rent.service.CarBrandService;

import javax.validation.Valid;
import java.util.Optional;


@Controller
@RequestMapping("/brand")
@Slf4j
public class CarBrandController {

    private final CarBrandService brandService;

    public CarBrandController(CarBrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("")
    public String index(Model model) {
        log.info("GET /brand");
        model.addAttribute("title", "Список марок");
        model.addAttribute("items", this.brandService.findAllActive());
        model.addAttribute("entity_type", "brand");
        model.addAttribute("list_name", "Марки автомобилей");
        return "list_view.html";
    }

    @GetMapping("/all")
    public String listAll(Model model) {
        log.info("GET /brand/all");
        model.addAttribute("title", "Полный список марок");
        model.addAttribute("items", this.brandService.findAll());
        model.addAttribute("entity_type", "brand");
        model.addAttribute("list_name", "Марки автомобилей");

        return "list_view.html";
    }

    @GetMapping("/add")
    public String add(Model model) {
        log.info("GET /brand/add");
        model.addAttribute("title", "Новая марка");
        model.addAttribute("action", "add");
        model.addAttribute("form", new BrandForm());
        model.addAttribute("entity_type", "brand");
        model.addAttribute("form_name", "Новая марка");

        return "name_form.html";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("form") BrandForm form, BindingResult result, Model model) {
        log.info("POST /brand/add");
        Optional<CarBrand> brand = this.brandService.findByName(form.getName().strip());
        if (brand.isPresent()) {
            if (brand.get().isActive()) {
                result.addError(new ObjectError("name", "Эта марка уже в базе данных!"));
            } else {
                brand.get().setActive(true);
                this.brandService.save(brand.get());
                return "redirect:/brand";
            }
        }
        if (result.hasErrors()) {
            model.addAttribute("title", "Новая марка");
            model.addAttribute("action", "add");
            model.addAttribute("form", form);
            model.addAttribute("entity_type", "brand");
            model.addAttribute("form_name", "Новая марка");

            return "name_form.html";
        }
        CarBrand newBrand = new CarBrand();
        newBrand.setName(form.getName().strip());
        newBrand.setActive(true);
        this.brandService.save(newBrand);
        return "redirect:/brand";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        log.info(String.format("GET /brand/update/%d", id));
        Optional<CarBrand> brand = this.brandService.findById(id);
        if (brand.isEmpty()) {
            return "redirect:/brand";
        }
        BrandForm form = new BrandForm();
        form.setName(brand.get().getName());
        form.setActive(brand.get().isActive());
        model.addAttribute("title", String.format("Изменение: %s", brand.get().getName()));
        model.addAttribute("action", String.format("update/%d", brand.get().getId()));
        model.addAttribute("form", form);
        model.addAttribute("entity_type", "brand");
        model.addAttribute("form_name", "Обновление марки");

        return "name_form.html";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("form") BrandForm form,
                         BindingResult result,
                         Model model) {
        log.info(String.format("POST /brand/update/%d", id));

        Optional<CarBrand> brand = this.brandService.findById(id);
        if (brand.isEmpty()) {
            return "redirect:/brand";
        }
        if (result.hasErrors()) {
            model.addAttribute("title", String.format("Изменение: %s", id));
            model.addAttribute("action", String.format("update/%d", id));
            model.addAttribute("form", form);
            model.addAttribute("entity_type", "brand");
            model.addAttribute("form_name", "Обновление марки");

            return "brand/name_form.html";
        }
        CarBrand u = new CarBrand();
        u.setId(id);
        u.setName(form.getName());
        u.setActive(form.isActive());
        this.brandService.update(u);
        return "redirect:/brand";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        this.brandService.deleteById(id);
        return "redirect:/brand";
    }
}
