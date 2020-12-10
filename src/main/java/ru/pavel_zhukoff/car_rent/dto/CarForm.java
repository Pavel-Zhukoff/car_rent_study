package ru.pavel_zhukoff.car_rent.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Getter
@Setter
public class CarForm {

    @NotEmpty
    @Size(min=6, max=6, message = "Введите номер машины в формате А000АА")
    private String number;

    private Integer run;

    private Double price;

    private boolean active = true;

    private Long colorId;

    private Long statusId;

    private Long modelId;

}
