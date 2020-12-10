package ru.pavel_zhukoff.car_rent.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "car")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String number;

    @Column(nullable = false)
    private Integer run;

    @Column(nullable = false)
    private Double price;

    @Column
    @ColumnDefault("true")
    private boolean active;

    @ManyToOne(targetEntity = CarColor.class)
    @JoinColumn(name = "color_id")
    private CarColor color;

    @ManyToOne(targetEntity = CarStatus.class)
    @JoinColumn(name = "status_id")
    private CarStatus status;

    @ManyToOne(targetEntity = CarModel.class)
    @JoinColumn(name = "model_id")
    private CarModel model;

    @OneToMany(targetEntity = CarRent.class, mappedBy = "car")
    private List<CarRent> rents;

    @Override
    public int hashCode() {
        return this.id.intValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !this.getClass().equals(obj.getClass())) return false;
        Car carObj = (Car) obj;
        return this.getId().equals(carObj.getId());
    }
}
