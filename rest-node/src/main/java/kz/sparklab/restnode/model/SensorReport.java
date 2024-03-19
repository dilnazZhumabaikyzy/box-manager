package kz.sparklab.restnode.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "sensor_report")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensorReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "box_id")
    private SmartBox box;
    private double fullness;
    private int fullnessPercentage;
    @CreationTimestamp
    private Date createdAt;
}
