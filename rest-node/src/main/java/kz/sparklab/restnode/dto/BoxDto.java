package kz.sparklab.restnode.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kz.sparklab.restnode.model.Box;
import kz.sparklab.restnode.model.BoxType;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoxDto {
    private String name;
    private String type;
    private String address;
    private int height;
    private int width;
    private int length;

    public BoxDto(Box box) {
        this.name = box.getName();
        this.type = box.getType().name();
        this.address = box.getAddress();
        this.height = box.getHeight();
        this.width = box.getWidth();
        this.length = box.getLength();
    }
}
