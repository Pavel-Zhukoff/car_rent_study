package ru.pavel_zhukoff.car_rent.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
public class ClientForm {

    @NotEmpty(message = "Имя не должно быть пустым!")
    private String firstName;

    @NotEmpty(message = "Фамилия не должна быть пустым!")
    private String lastName;

    private String thirdName = "";

    @Size(min = 11, max = 11, message = "Введите номер телефона в формате 89991110022")
    @NotEmpty(message = "Номер не должен быть пустым!")
    private String phone;

    @Size(min = 10, max = 10, message = "Серию и номер паспорта в формате 1122345678")
    @NotEmpty(message = "Паспорт не должен быть пустым!")
    private String passport;

    @Size(min = 10, max = 10, message = "Введите номер телефона в формате 1122333444")
    @NotEmpty(message = "Номер ВУ не должен быть пустым!")
    private String driverId;

    private boolean active = true;
}
