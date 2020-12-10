package ru.pavel_zhukoff.car_rent.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.pavel_zhukoff.car_rent.dto.RentForm;
import ru.pavel_zhukoff.car_rent.model.Car;
import ru.pavel_zhukoff.car_rent.model.CarRent;
import ru.pavel_zhukoff.car_rent.model.Client;
import ru.pavel_zhukoff.car_rent.service.CarService;
import ru.pavel_zhukoff.car_rent.service.CarRentService;
import ru.pavel_zhukoff.car_rent.service.ClientService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/rent")
@Slf4j
public class CarRentController {

    private final CarRentService rentService;
    private final CarService carService;
    private final ClientService clientService;

    public CarRentController(CarRentService rentService, CarService carService, ClientService clientService) {
        this.rentService = rentService;
        this.carService = carService;
        this.clientService = clientService;
    }

    @GetMapping("")
    public String index(Model model) {
        log.info("GET /model");
        model.addAttribute("title", "Список аренд");
        model.addAttribute("items", this.rentService.findAllActive());
        model.addAttribute("entity_type", "rent");
        model.addAttribute("list_name", "Аренды автомобилей");
        return "rent_list.html";
    }

    @GetMapping("/all")
    public String listAll(Model model) {
        log.info("GET /model/all");
        model.addAttribute("title", "Полный список аренд");
        model.addAttribute("items", this.rentService.findAll());
        model.addAttribute("entity_type", "rent");
        model.addAttribute("list_name", "Все аренды автомобилей");
        return "rent_list.html";
    }

    @GetMapping("/add")
    public String add(Model model) {
        log.info("GET /model/add");
        model.addAttribute("title", "Новая аренда");
        model.addAttribute("action", "add");
        model.addAttribute("form", new RentForm());
        model.addAttribute("entity_type", "rent");
        model.addAttribute("form_name", "Новая аренда");
        Map<Long, String> cars = this.carService.findAllActive()
                .stream()
                .collect(Collectors.toMap(Car::getId, c -> String.format("%s - %s", c.getNumber(), c.getModel().getFullName())));
        Map<Long, String> clients = this.clientService.findAllActive()
                .stream()
                .collect(Collectors.toMap(Client::getId, c -> String.format("%s - %s", c.getPhone(), c.getDriverId())));
        model.addAttribute("carSelect", cars);
        model.addAttribute("clientSelect", clients);
        return "rent_form.html";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("form") RentForm form, BindingResult result, Model model) {
        log.info("POST /model/add");
        Optional<Car> car = this.carService.findById(form.getCarId());
        Optional<Client> client = this.clientService.findById(form.getClientId());
        if (car.isEmpty()) {
            result.addError(new ObjectError("carId", "Выбран несуществующий автомобиль!"));
        }
        if (client.isEmpty()) {
            result.addError(new ObjectError("clientId", "Выбран несуществующий клиент!"));
        }
        if (form.getStartDate().after(form.getEndDate())) {
            result.addError(new ObjectError("startDate", "Время старта не должно быть после времени окончания!"));
        }
        if (car.isPresent()) {
            List<CarRent> carRents = this.rentService.findAllByCar(car.get());
            for (CarRent rent: carRents) {
                if (rent.getStartDate().after(form.getStartDate()) && rent.getStartDate().before(form.getEndDate()) ||
                        rent.getEndDate().after(form.getStartDate()) && rent.getEndDate().before(form.getEndDate())) {
                    result.addError(new ObjectError("carId", "Автомобиль уже забронирован"));
                }
            }
        }
        if (result.hasErrors()) {
            model.addAttribute("title", "Новая аренда");
            model.addAttribute("action", "add");
            model.addAttribute("form", form);
            model.addAttribute("entity_type", "rent");
            model.addAttribute("form_name", "Новая аренда");
            Map<Long, String> cars = this.carService.findAllActive()
                    .stream()
                    .collect(Collectors.toMap(Car::getId, c -> String.format("%s - %s", c.getNumber(), c.getModel().getFullName())));
            Map<Long, String> clients = this.clientService.findAllActive()
                    .stream()
                    .collect(Collectors.toMap(Client::getId, c -> String.format("%s - %s", c.getPhone(), c.getDriverId())));
            model.addAttribute("carSelect", cars);
            model.addAttribute("clientSelect", clients);
            return "rent_form.html";
        }
        CarRent newRent = new CarRent();
        newRent.setCar(car.get());
        newRent.setClient(client.get());
        newRent.setStartDate(form.getStartDate());
        newRent.setEndDate(form.getEndDate());
        newRent.setActive(true);
        this.rentService.save(newRent);
        return "redirect:/rent";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        log.info(String.format("GET /model/update/%d", id));
        Optional<CarRent> carRent = this.rentService.findById(id);
        if (carRent.isEmpty()) {
            return "redirect:/rent";
        }

        RentForm form = new RentForm();
        form.setCarId(carRent.get().getCar().getId());
        form.setClientId(carRent.get().getClient().getId());
        form.setStartDate(carRent.get().getStartDate());
        form.setEndDate(carRent.get().getEndDate());
        form.setActive(carRent.get().isActive());
        model.addAttribute("title", String.format("Изменение аренды: %d", carRent.get().getId()));
        model.addAttribute("action", String.format("update/%d", carRent.get().getId()));
        model.addAttribute("form", form);
        model.addAttribute("entity_type", "rent");
        model.addAttribute("form_name", "Обновление Аренды");
        Map<Long, String> cars = this.carService.findAllActive()
                .stream()
                .collect(Collectors.toMap(Car::getId, c -> String.format("%s - %s", c.getNumber(), c.getModel().getFullName())));
        Map<Long, String> clients = this.clientService.findAllActive()
                .stream()
                .collect(Collectors.toMap(Client::getId, c -> String.format("%s - %s", c.getPhone(), c.getDriverId())));
        model.addAttribute("carSelect", cars);
        model.addAttribute("clientSelect", clients);
        return "rent_form.html";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("form") RentForm form,
                         BindingResult result,
                         Model model) {
        log.info(String.format("POST /model/update/%d", id));
        Optional<CarRent> carRent = this.rentService.findById(id);
        if (carRent.isEmpty()) {
            return "redirect:/rent";
        }
        Optional<Car> car = this.carService.findById(form.getCarId());
        Optional<Client> client = this.clientService.findById(form.getClientId());
        if (car.isEmpty()) {
            result.addError(new ObjectError("carId", "Выбран несуществующий автомобиль!"));
        }
        if (client.isEmpty()) {
            result.addError(new ObjectError("clientId", "Выбран несуществующий клиент!"));
        }
        if (form.getStartDate().after(form.getEndDate())) {
            result.addError(new ObjectError("startDate", "Время старта не должно быть после времени окончания!"));
        }
        if (car.isPresent()) {
            List<CarRent> carRents = this.rentService.findAllByCar(car.get());
            for (CarRent rent: carRents) {
                if (rent.equals(carRent.get())) continue;
                if (rent.getStartDate().after(form.getStartDate()) && rent.getStartDate().before(form.getEndDate()) ||
                        rent.getEndDate().after(form.getStartDate()) && rent.getEndDate().before(form.getEndDate())) {
                    result.addError(new ObjectError("carId", "Автомобиль уже забронирован"));
                }
            }
        }
        if (result.hasErrors()) {
            model.addAttribute("title", String.format("Изменение аренды: %d", carRent.get().getId()));
            model.addAttribute("action", String.format("update/%d", carRent.get().getId()));
            model.addAttribute("form", form);
            model.addAttribute("entity_type", "rent");
            model.addAttribute("form_name", "Обновление Аренды");
            Map<Long, String> cars = this.carService.findAllActive()
                    .stream()
                    .collect(Collectors.toMap(Car::getId, c -> String.format("%s - %s", c.getNumber(), c.getModel().getFullName())));
            Map<Long, String> clients = this.clientService.findAllActive()
                    .stream()
                    .collect(Collectors.toMap(Client::getId, c -> String.format("%s - %s", c.getPhone(), c.getDriverId())));
            model.addAttribute("carSelect", cars);
            model.addAttribute("clientSelect", clients);
            return "model/rent_form.html";
        }
        carRent.get().setCar(car.get());
        carRent.get().setClient(client.get());
        carRent.get().setStartDate(form.getStartDate());
        carRent.get().setEndDate(form.getEndDate());
        carRent.get().setActive(form.isActive());
        this.rentService.update(carRent.get());
        return "redirect:/rent";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        this.rentService.deleteById(id);
        return "redirect:/rent";
    }
}
