package ru.pavel_zhukoff.car_rent.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.pavel_zhukoff.car_rent.dto.StatusForm;
import ru.pavel_zhukoff.car_rent.model.CarStatus;
import ru.pavel_zhukoff.car_rent.service.CarStatusService;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/status")
@Slf4j
public class CarStatusController {
    private final CarStatusService statusService;

    public CarStatusController(CarStatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping("")
    public String index(Model model) {
        log.info("GET /status");
        model.addAttribute("title", "Список статусов");
        model.addAttribute("items", this.statusService.findAllActive());
        model.addAttribute("entity_type", "status");
        model.addAttribute("list_name", "Статусы автомобилей");
        return "list_view.html";
    }

    @GetMapping("/all")
    public String listAll(Model model) {
        log.info("GET /status/all");
        model.addAttribute("title", "Полный список статусов");
        model.addAttribute("items", this.statusService.findAll());
        model.addAttribute("entity_type", "status");
        model.addAttribute("list_name", "Все статусы автомобилей");
        return "list_view.html";
    }

    @GetMapping("/add")
    public String add(Model model) {
        log.info("GET /status/add");
        model.addAttribute("title", "Новый статус");
        model.addAttribute("action", "add");
        model.addAttribute("form", new StatusForm());
        model.addAttribute("entity_type", "status");
        model.addAttribute("form_name", "Новый статус");
        return "name_form.html";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("form") StatusForm form, BindingResult result, Model model) {
        log.info("POST /status/add");
        String name = StringUtils.capitalize(form.getName().strip());
        Optional<CarStatus> status = this.statusService.findByName(name);
        if (status.isPresent()) {
            if (status.get().isActive()) {
                result.addError(new ObjectError("name", "Этот статус уже в базе данных!"));
            } else {
                status.get().setActive(true);
                this.statusService.save(status.get());
                return "redirect:/status";
            }
        }
        if (result.hasErrors()) {
            model.addAttribute("title", "Новый статус");
            model.addAttribute("action", "add");
            model.addAttribute("form", form);
            model.addAttribute("entity_type", "status");
            model.addAttribute("form_name", "Новый статус");
            return "name_form.html";
        }
        CarStatus newStatus = new CarStatus();
        newStatus.setName(name);
        newStatus.setActive(true);
        this.statusService.save(newStatus);
        return "redirect:/status";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        log.info(String.format("GET /status/update/%d", id));
        Optional<CarStatus> status = this.statusService.findById(id);
        if (status.isEmpty()) {
            return "redirect:/status";
        }
        StatusForm form = new StatusForm();
        form.setName(status.get().getName());
        form.setActive(status.get().isActive());
        model.addAttribute("title", String.format("Изменение: %s", status.get().getName()));
        model.addAttribute("action", String.format("update/%d", status.get().getId()));
        model.addAttribute("form", form);
        model.addAttribute("entity_type", "status");
        model.addAttribute("form_name", "Обновление статуса");

        return "name_form.html";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("form") StatusForm form,
                         BindingResult result,
                         Model model) {
        log.info(String.format("POST /status/update/%d", id));
        Optional<CarStatus> status = this.statusService.findById(id);
        if (status.isEmpty()) {
            return "redirect:/status";
        }
        if (result.hasErrors()) {
            model.addAttribute("title", String.format("Изменение: %s", id));
            model.addAttribute("action", String.format("update/%d", id));
            model.addAttribute("form", form);
            model.addAttribute("entity_type", "status");
            model.addAttribute("form_name", "Обновление статуса");
            return "status/name_form.html";
        }
        status.get().setName(form.getName());
        status.get().setActive(form.isActive());
        this.statusService.update(status.get());
        return "redirect:/status";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        this.statusService.deleteById(id);
        return "redirect:/status";
    }
}
