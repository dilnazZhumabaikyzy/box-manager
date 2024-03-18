package kz.sparklab.restnode.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "smart_box")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmartBox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BoxType type;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private int height;
    @Column(nullable = false)
    private int width;
    @Column(nullable = false)
    private int length;
    private boolean isActive;
    private int sensorHeight;

    @OneToMany(mappedBy = "box", cascade = CascadeType.REMOVE)
    private List<SensorReport> reports;
}
