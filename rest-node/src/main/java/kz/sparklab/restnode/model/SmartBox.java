package kz.sparklab.restnode.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "smart_box")
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "box_id")
public class SmartBox extends Box{
    @Id
    @Column(name = "box_id")
    private Long id;
    private boolean isActive;
    private int sensorHeight;
    @OneToMany(mappedBy="box")
    private Set<SensorReport>  reports = new HashSet<>();

}
