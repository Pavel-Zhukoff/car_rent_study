package ru.pavel_zhukoff.car_rent.model;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "car_status")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CarStatus {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(targetEntity = Car.class, cascade = CascadeType.ALL, mappedBy = "status")
    private List<Car> cars;

    @Column(columnDefinition = "boolean default true")
    private boolean active;

    @Override
    public int hashCode() {
        return this.id.intValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !this.getClass().equals(obj.getClass())) return false;
        CarStatus statusObj = (CarStatus) obj;
        return this.getId().equals(statusObj.getId());
    }

}
