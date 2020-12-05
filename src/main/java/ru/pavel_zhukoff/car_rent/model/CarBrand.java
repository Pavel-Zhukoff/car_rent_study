package ru.pavel_zhukoff.car_rent.model;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "car_brand")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CarBrand {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(columnDefinition = "boolean default true")
    private boolean active;

    @OneToMany(targetEntity = CarModel.class, cascade = CascadeType.ALL, mappedBy = "brand", fetch = FetchType.LAZY)
    private List<CarModel> brands;

    @Override
    public int hashCode() {
        return this.id.intValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !this.getClass().equals(obj.getClass())) return false;
        CarBrand brandObj = (CarBrand) obj;
        return this.getId().equals(brandObj.getId());
    }
}
