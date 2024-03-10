package kz.sparklab.restnode.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "smart_box")
@Getter
@Setter

public class SmartBox{
    @Id
    @Column(name = "box_id")
    private Long id;
    private boolean isActive;
    private int sensorHeight;
    @OneToMany(mappedBy="box")
    private Set<SensorReport>  reports = new HashSet<>();
    @OneToOne
    @MapsId
    @JoinColumn(name = "box_id")
    private Box box;
}
