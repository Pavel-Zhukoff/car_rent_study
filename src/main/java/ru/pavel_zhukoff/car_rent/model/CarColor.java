package ru.pavel_zhukoff.car_rent.model;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "car_color")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CarColor {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(columnDefinition = "boolean default true")
    private boolean active;

    @OneToMany(targetEntity = Car.class, cascade = CascadeType.ALL, mappedBy = "color")
    private List<Car> cars;

    @Override
    public int hashCode() {
        return this.id.intValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !this.getClass().equals(obj.getClass())) return false;
        CarColor colorObj = (CarColor) obj;
        return this.getId().equals(colorObj.getId());
    }
}





