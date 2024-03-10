package kz.sparklab.restnode.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "sensor_report")
@Getter
@Setter
public class SensorReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="box_id")
    private SmartBox box;
    private double fullness;
    @CreationTimestamp
    private Date createdAt;
}
