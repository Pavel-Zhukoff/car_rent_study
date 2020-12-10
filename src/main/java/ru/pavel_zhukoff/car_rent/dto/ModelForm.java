package ru.pavel_zhukoff.car_rent.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
public class ModelForm {

    @Size(min = 2, message = "Названеи не может быть менььше 2-х символов")
    @NotEmpty(message = "Название не должно быть пустым!")
    private String name;

    private Long brandId;

    private boolean active = true;

}
