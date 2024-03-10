package kz.sparklab.restnode.model;


import jakarta.persistence.*;
import kz.sparklab.restnode.dto.SmartBoxDto;
import lombok.*;

@Entity
@Table(name = "box")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class Box {
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
}
