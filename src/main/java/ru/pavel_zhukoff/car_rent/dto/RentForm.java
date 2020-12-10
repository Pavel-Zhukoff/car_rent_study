package ru.pavel_zhukoff.car_rent.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class RentForm {

    private Long carId;

    private Long clientId;

    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    private Date startDate;

    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    private Date endDate;

    private boolean active = true;
}
