package ru.pavel_zhukoff.car_rent.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.pavel_zhukoff.car_rent.dto.ModelForm;
import ru.pavel_zhukoff.car_rent.model.CarBrand;
import ru.pavel_zhukoff.car_rent.model.CarModel;
import ru.pavel_zhukoff.car_rent.service.CarBrandService;
import ru.pavel_zhukoff.car_rent.service.CarModelService;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/model")
@Slf4j
public class CarModelController {
    private final CarModelService modelService;
    private final CarBrandService brandService;

    public CarModelController(CarModelService modelService, CarBrandService brandService) {
        this.modelService = modelService;
        this.brandService = brandService;
    }

    @GetMapping("")
    public String index(Model model) {
        log.info("GET /model");
        model.addAttribute("title", "Список моделей");
        model.addAttribute("items", this.modelService.findAllActive());
        model.addAttribute("entity_type", "model");
        model.addAttribute("list_name", "Модели автомобилей");
        return "list_view.html";
    }

    @GetMapping("/all")
    public String listAll(Model model) {
        log.info("GET /model/all");
        model.addAttribute("title", "Полный список моделей");
        model.addAttribute("items", this.modelService.findAll());
        model.addAttribute("entity_type", "model");
        model.addAttribute("list_name", "Все модели автомобилей");
        return "list_view.html";
    }

    @GetMapping("/add")
    public String add(Model model) {
        log.info("GET /model/add");
        model.addAttribute("title", "Новая модель");
        model.addAttribute("action", "add");
        model.addAttribute("form", new ModelForm());
        model.addAttribute("entity_type", "model");
        model.addAttribute("form_name", "Новая модель");
        Map<Long, String> brands = this.brandService.findAllActive()
                .stream().collect(Collectors.toMap(CarBrand::getId, CarBrand::getName));
        model.addAttribute("brandSelect", brands);
        return "model_form.html";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("form") ModelForm form, BindingResult result, Model model) {
        log.info("POST /model/add");
        String name = StringUtils.capitalize(form.getName().strip());
        Optional<CarModel> carModel = this.modelService.findByName(name);
        Optional<CarBrand> carBrand = this.brandService.findById(form.getBrandId());
        if (carModel.isPresent()) {
            if (carModel.get().isActive()) {
                result.addError(new ObjectError("name", "Эта модель уже в базе данных!"));
            } else {
                carModel.get().setActive(true);
                this.modelService.save(carModel.get());
                return "redirect:/model";
            }
        }
        if (carBrand.isEmpty()) {
            result.addError(new ObjectError("brandId", "Выбран несуществующий бренд!"));
        }
        if (result.hasErrors()) {
            model.addAttribute("title", "Новая модель");
            model.addAttribute("action", "add");
            model.addAttribute("form", form);
            model.addAttribute("entity_type", "model");
            model.addAttribute("form_name", "Новая модель");
            Map<Long, String> brands = this.brandService.findAllActive()
                    .stream().collect(Collectors.toMap(CarBrand::getId, CarBrand::getName));
            model.addAttribute("brandSelect", brands);
            return "model_form.html";
        }
        CarModel newModel = new CarModel();
        newModel.setName(name);
        newModel.setBrand(carBrand.get());
        newModel.setActive(true);
        this.modelService.save(newModel);
        return "redirect:/model";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        log.info(String.format("GET /model/update/%d", id));
        Optional<CarModel> carModel = this.modelService.findById(id);
        if (carModel.isEmpty()) {
            return "redirect:/model";
        }
        ModelForm form = new ModelForm();
        form.setName(carModel.get().getName());
        form.setActive(carModel.get().isActive());
        model.addAttribute("title", String.format("Изменение: %s", carModel.get().getName()));
        model.addAttribute("action", String.format("update/%d", carModel.get().getId()));
        model.addAttribute("form", form);
        model.addAttribute("entity_type", "model");
        model.addAttribute("form_name", "Обновление модели");
        Map<Long, String> brands = this.brandService.findAllActive()
                .stream().collect(Collectors.toMap(CarBrand::getId, CarBrand::getName));
        model.addAttribute("brandSelect", brands);
        return "model_form.html";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("form") ModelForm form,
                         BindingResult result,
                         Model model) {
        log.info(String.format("POST /model/update/%d", id));
        Optional<CarModel> carModel = this.modelService.findById(id);
        Optional<CarBrand> carBrand = this.brandService.findById(form.getBrandId());
        if (carModel.isEmpty()) {
            return "redirect:/model";
        }
        if (carBrand.isEmpty()) {
            result.addError(new ObjectError("brandId", "Выбран несуществующий бренд!"));
        }
        if (result.hasErrors()) {
            model.addAttribute("title", String.format("Изменение: %s", id));
            model.addAttribute("action", String.format("update/%d", id));
            model.addAttribute("form", form);
            model.addAttribute("entity_type", "model");
            model.addAttribute("form_name", "Обновление модели");
            Map<Long, String> brands = this.brandService.findAllActive()
                    .stream().collect(Collectors.toMap(CarBrand::getId, CarBrand::getName));
            model.addAttribute("brandSelect", brands);
            return "model/model_form.html";
        }
        carModel.get().setName(form.getName());
        carModel.get().setBrand(carBrand.get());
        carModel.get().setActive(form.isActive());
        this.modelService.update(carModel.get());
        return "redirect:/model";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        this.modelService.deleteById(id);
        return "redirect:/model";
    }
}
