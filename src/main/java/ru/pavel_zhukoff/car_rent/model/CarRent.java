package ru.pavel_zhukoff.car_rent.model;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "rent")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CarRent {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(targetEntity = Car.class)
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne(targetEntity = Client.class)
    @JoinColumn(name = "client_id")
    private Client client;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

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
        CarRent rentObj = (CarRent) obj;
        return this.getId().equals(rentObj.getId());
    }
}
