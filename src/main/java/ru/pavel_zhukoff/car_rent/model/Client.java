package ru.pavel_zhukoff.car_rent.model;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "client")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String phone;

    @Column(unique = true, nullable = false)
    private String driverId;

    @OneToMany(targetEntity = CarRent.class, mappedBy = "client")
    private List<CarRent> rents;

    @Column
    @ColumnDefault("true")
    private boolean active;

    @Override
    public int hashCode() {
        return this.id.intValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !this.getClass().equals(obj.getClass())) return false;
        Client clientObj = (Client) obj;
        return this.getId().equals(clientObj.getId());
    }
}


