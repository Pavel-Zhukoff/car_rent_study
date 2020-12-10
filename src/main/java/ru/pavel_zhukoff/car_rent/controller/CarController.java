package ru.pavel_zhukoff.car_rent.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.pavel_zhukoff.car_rent.dto.CarForm;
import ru.pavel_zhukoff.car_rent.model.Car;
import ru.pavel_zhukoff.car_rent.model.CarColor;
import ru.pavel_zhukoff.car_rent.model.CarModel;
import ru.pavel_zhukoff.car_rent.model.CarStatus;
import ru.pavel_zhukoff.car_rent.service.CarColorService;
import ru.pavel_zhukoff.car_rent.service.CarModelService;
import ru.pavel_zhukoff.car_rent.service.CarService;
import ru.pavel_zhukoff.car_rent.service.CarStatusService;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/car")
@Slf4j
public class CarController {
    private final CarService carService;
    private final CarModelService modelService;
    private final CarColorService colorService;
    private final CarStatusService statusService;

    public CarController(CarService carService,
                         CarModelService modelService,
                         CarColorService colorService,
                         CarStatusService statusService) {
        this.carService = carService;
        this.modelService = modelService;
        this.colorService = colorService;
        this.statusService = statusService;
    }

    @GetMapping("")
    public String index(Model model) {
        log.info("GET /model");
        model.addAttribute("title", "Список машин");
        model.addAttribute("items", this.carService.findAllActive());
        model.addAttribute("entity_type", "car");
        model.addAttribute("list_name", "Машины");
        return "car_list.html";
    }

    @GetMapping("/all")
    public String listAll(Model model) {
        log.info("GET /car/all");
        model.addAttribute("title", "Полный список машин");
        model.addAttribute("items", this.carService.findAll());
        model.addAttribute("entity_type", "car");
        model.addAttribute("list_name", "Все машины");
        return "car_list.html";
    }

    @GetMapping("/add")
    public String add(Model model) {
        log.info("GET /car/add");
        model.addAttribute("title", "Новая машина");
        model.addAttribute("action", "add");
        model.addAttribute("form", new CarForm());
        model.addAttribute("entity_type", "car");
        model.addAttribute("form_name", "Новая машина");
        Map<Long, String> modelSelect = this.modelService.findAllActive()
                .stream().collect(Collectors.toMap(CarModel::getId, CarModel::getFullName));
        Map<Long, String> colorSelect = this.colorService.findAllActive()
                .stream().collect(Collectors.toMap(CarColor::getId, CarColor::getName));
        Map<Long, String> statusSelect = this.statusService.findAllActive()
                .stream().collect(Collectors.toMap(CarStatus::getId, CarStatus::getName));
        model.addAttribute("modelSelect", modelSelect);
        model.addAttribute("colorSelect", colorSelect);
        model.addAttribute("statusSelect", statusSelect);
        return "car_form.html";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("form") CarForm form, BindingResult result, Model model) {
        log.info("POST /car/add");
        Optional<CarModel> carModel = this.modelService.findById(form.getModelId());
        Optional<CarColor> carColor = this.colorService.findById(form.getColorId());
        Optional<CarStatus> carStatus = this.statusService.findById(form.getStatusId());
        if (carModel.isEmpty()) {
            result.addError(new ObjectError("brandId", "Выбран несуществующий бренд!"));
        }
        if (carColor.isEmpty()) {
            result.addError(new ObjectError("colorId", "Выбран несуществующий цвет!"));
        }
        if (carStatus.isEmpty()) {
            result.addError(new ObjectError("statusId", "Выбран несуществующий статус!"));
        }
        if (result.hasErrors()) {
            model.addAttribute("title", "Новая машина");
            model.addAttribute("action", "add");
            model.addAttribute("form", form);
            model.addAttribute("entity_type", "car");
            model.addAttribute("form_name", "Новая машина");
            Map<Long, String> modelSelect = this.modelService.findAllActive()
                    .stream().collect(Collectors.toMap(CarModel::getId, CarModel::getFullName));
            Map<Long, String> colorSelect = this.colorService.findAllActive()
                    .stream().collect(Collectors.toMap(CarColor::getId, CarColor::getName));
            Map<Long, String> statusSelect = this.statusService.findAllActive()
                    .stream().collect(Collectors.toMap(CarStatus::getId, CarStatus::getName));
            model.addAttribute("modelSelect", modelSelect);
            model.addAttribute("colorSelect", colorSelect);
            model.addAttribute("statusSelect", statusSelect);
            return "car_form.html";
        }
        Car newCar = new Car();
        newCar.setPrice(form.getPrice());
        newCar.setNumber(form.getNumber());
        newCar.setRun(form.getRun());
        newCar.setActive(true);
        newCar.setModel(carModel.get());
        newCar.setColor(carColor.get());
        newCar.setStatus(carStatus.get());
        this.carService.save(newCar);
        return "redirect:/car";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        log.info(String.format("GET /car/update/%d", id));
        Optional<Car> car = this.carService.findById(id);
        if (car.isEmpty()) {
            return "redirect:/car";
        }
        CarForm form = new CarForm();
        form.setPrice(car.get().getPrice());
        form.setNumber(car.get().getNumber());
        form.setRun(car.get().getRun());
        form.setActive(car.get().isActive());
        form.setModelId(car.get().getModel().getId());
        form.setColorId(car.get().getColor().getId());
        form.setStatusId(car.get().getStatus().getId());
        model.addAttribute("title", String.format("Изменение: %s", car.get().getNumber()));
        model.addAttribute("action", String.format("update/%d", car.get().getId()));
        model.addAttribute("form", form);
        model.addAttribute("entity_type", "car");
        model.addAttribute("form_name", "Обновление модели");
        Map<Long, String> modelSelect = this.modelService.findAllActive()
                .stream().collect(Collectors.toMap(CarModel::getId, CarModel::getFullName));
        Map<Long, String> colorSelect = this.colorService.findAllActive()
                .stream().collect(Collectors.toMap(CarColor::getId, CarColor::getName));
        Map<Long, String> statusSelect = this.statusService.findAllActive()
                .stream().collect(Collectors.toMap(CarStatus::getId, CarStatus::getName));
        model.addAttribute("modelSelect", modelSelect);
        model.addAttribute("colorSelect", colorSelect);
        model.addAttribute("statusSelect", statusSelect);
        return "car_form.html";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("form") CarForm form,
                         BindingResult result,
                         Model model) {
        log.info(String.format("POST /car/update/%d", id));
        Optional<Car> car = this.carService.findById(id);
        Optional<CarModel> carModel = this.modelService.findById(form.getModelId());
        Optional<CarColor> carColor = this.colorService.findById(form.getColorId());
        Optional<CarStatus> carStatus = this.statusService.findById(form.getStatusId());
        if (car.isEmpty()) {
            return "redirect:/car";
        }
        if (carModel.isEmpty()) {
            result.addError(new ObjectError("brandId", "Выбран несуществующий бренд!"));
        }
        if (carColor.isEmpty()) {
            result.addError(new ObjectError("colorId", "Выбран несуществующий цвет!"));
        }
        if (carStatus.isEmpty()) {
            result.addError(new ObjectError("statusId", "Выбран несуществующий статус!"));
        }
        if (result.hasErrors()) {
            model.addAttribute("title", String.format("Изменение: %s", id));
            model.addAttribute("action", String.format("update/%d", id));
            model.addAttribute("form", form);
            model.addAttribute("entity_type", "car");
            model.addAttribute("form_name", "Обновление машины");
            Map<Long, String> modelSelect = this.modelService.findAllActive()
                    .stream().collect(Collectors.toMap(CarModel::getId, CarModel::getFullName));
            Map<Long, String> colorSelect = this.colorService.findAllActive()
                    .stream().collect(Collectors.toMap(CarColor::getId, CarColor::getName));
            Map<Long, String> statusSelect = this.statusService.findAllActive()
                    .stream().collect(Collectors.toMap(CarStatus::getId, CarStatus::getName));
            model.addAttribute("modelSelect", modelSelect);
            model.addAttribute("colorSelect", colorSelect);
            model.addAttribute("statusSelect", statusSelect);
            return "car_form.html";
        }
        car.get().setPrice(form.getPrice());
        car.get().setNumber(form.getNumber());
        car.get().setRun(form.getRun());
        car.get().setActive(true);
        car.get().setModel(carModel.get());
        car.get().setColor(carColor.get());
        car.get().setStatus(carStatus.get());
        this.carService.update(car.get());
        return "redirect:/car";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        this.carService.deleteById(id);
        return "redirect:/car";
    }
}
