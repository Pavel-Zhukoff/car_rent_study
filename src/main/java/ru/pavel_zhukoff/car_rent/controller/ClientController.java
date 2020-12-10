package ru.pavel_zhukoff.car_rent.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.pavel_zhukoff.car_rent.dto.ClientForm;
import ru.pavel_zhukoff.car_rent.model.CarBrand;
import ru.pavel_zhukoff.car_rent.model.Client;
import ru.pavel_zhukoff.car_rent.service.CarBrandService;
import ru.pavel_zhukoff.car_rent.service.ClientService;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/client")
@Slf4j
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("")
    public String index(Model model) {
        log.info("GET /model");
        model.addAttribute("title", "Список клиентов");
        model.addAttribute("items", this.clientService.findAllActive());
        model.addAttribute("entity_type", "client");
        model.addAttribute("list_name", "Клиенты сервиса");
        return "client_list.html";
    }

    @GetMapping("/all")
    public String listAll(Model model) {
        log.info("GET /client/all");
        model.addAttribute("title", "Полный список клиентов");
        model.addAttribute("items", this.clientService.findAll());
        model.addAttribute("entity_type", "client");
        model.addAttribute("list_name", "Все клиенты сервиса");
        return "client_list.html";
    }

    @GetMapping("/add")
    public String add(Model model) {
        log.info("GET /client/add");
        model.addAttribute("title", "Новый клиент");
        model.addAttribute("action", "add");
        model.addAttribute("form", new ClientForm());
        model.addAttribute("entity_type", "client");
        model.addAttribute("form_name", "Новый клиент");
        return "client_form.html";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("form") ClientForm form, BindingResult result, Model model) {
        log.info("POST /client/add");
        String driverId = form.getDriverId().strip();
        String phone = form.getPhone().strip();
        String passport = form.getPassport().strip();
        Optional<Client> clientCheckDriverId = this.clientService.findByDriverId(driverId);
        Optional<Client> clientCheckPhone = this.clientService.findByPhone(phone);
        Optional<Client> clientCheckPassport = this.clientService.findByPassport(passport);
        if (clientCheckDriverId.isPresent()) {
            if (clientCheckDriverId.get().isActive()) {
                result.addError(new ObjectError("driverId", "Этот номер ВУ уже в базе данных!"));
            } else {
                clientCheckDriverId.get().setActive(true);
                this.clientService.save(clientCheckDriverId.get());
                return "redirect:/client";
            }
        }
        if (clientCheckPhone.isPresent()) {
            if (clientCheckPhone.get().isActive()) {
                result.addError(new ObjectError("phone", "Этот номер уже в базе данных!"));
            } else {
                clientCheckPhone.get().setActive(true);
                this.clientService.save(clientCheckPhone.get());
                return "redirect:/client";
            }
        }
        if (clientCheckPassport.isPresent()) {
            if (clientCheckPassport.get().isActive()) {
                result.addError(new ObjectError("passport", "Этот паспорт уже в базе данных!"));
            } else {
                clientCheckPassport.get().setActive(true);
                this.clientService.save(clientCheckPassport.get());
                return "redirect:/client";
            }
        }
        if (result.hasErrors()) {
            model.addAttribute("title", "Новый клиент");
            model.addAttribute("action", "add");
            model.addAttribute("form", form);
            model.addAttribute("entity_type", "client");
            model.addAttribute("form_name", "Новый клиент");
            return "client_form.html";
        }
        Client newClient = new Client();
        newClient.setFirstName(form.getFirstName());
        newClient.setLastName(form.getLastName());
        newClient.setThirdName(form.getThirdName());
        newClient.setPassport(passport);
        newClient.setDriverId(driverId);
        newClient.setPhone(phone);
        newClient.setActive(true);
        this.clientService.save(newClient);
        return "redirect:/client";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        log.info(String.format("GET /client/update/%d", id));
        Optional<Client> clientModel = this.clientService.findById(id);
        if (clientModel.isEmpty()) {
            return "redirect:/client";
        }
        ClientForm form = new ClientForm();
        form.setDriverId(clientModel.get().getDriverId());
        form.setFirstName(clientModel.get().getFirstName());
        form.setLastName(clientModel.get().getLastName());
        form.setThirdName(clientModel.get().getThirdName());
        form.setPassport(clientModel.get().getPassport());
        form.setPhone(clientModel.get().getPhone());
        form.setActive(clientModel.get().isActive());
        model.addAttribute("title", String.format("Изменение данных клиента с ВУ: %s", clientModel.get().getDriverId()));
        model.addAttribute("action", String.format("update/%d", clientModel.get().getId()));
        model.addAttribute("form", form);
        model.addAttribute("entity_type", "client");
        model.addAttribute("form_name", "Обновление данных клиента");
        return "client_form.html";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("form") ClientForm form,
                         BindingResult result,
                         Model model) {
        log.info(String.format("POST /client/update/%d", id));
        Optional<Client> clientModel = this.clientService.findById(id);
        if (clientModel.isEmpty()) {
            return "redirect:/client";
        }
        if (result.hasErrors()) {
            model.addAttribute("title", String.format("Изменение данных клиента с ВУ: %s", id));
            model.addAttribute("action", String.format("update/%d", id));
            model.addAttribute("form", form);
            model.addAttribute("entity_type", "client");
            model.addAttribute("form_name", "Обновление данных клиента");
            return "model/client_form.html";
        }

        clientModel.get().setFirstName(form.getFirstName());
        clientModel.get().setLastName(form.getLastName());
        clientModel.get().setThirdName(form.getThirdName());
        clientModel.get().setPassport(form.getPassport().strip());
        clientModel.get().setDriverId(form.getDriverId().strip());
        clientModel.get().setPhone(form.getPhone().strip());
        clientModel.get().setActive(form.isActive());
        this.clientService.update(clientModel.get());
        return "redirect:/client";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        this.clientService.deleteById(id);
        return "redirect:/client";
    }
}
