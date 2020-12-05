package ru.pavel_zhukoff.car_rent.model;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "car_model")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CarModel {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column
    @ColumnDefault("true")
    private boolean active;

    @ManyToOne(targetEntity = CarBrand.class)
    @JoinColumn(name = "brand_id")
    private CarBrand brand;

    @OneToMany(targetEntity = Car.class, cascade = CascadeType.ALL, mappedBy = "model")
    private List<Car> cars;

    @Override
    public int hashCode() {
        return this.id.intValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !this.getClass().equals(obj.getClass())) return false;
        CarModel modelObj = (CarModel) obj;
        return this.getId().equals(modelObj.getId());
    }

}
