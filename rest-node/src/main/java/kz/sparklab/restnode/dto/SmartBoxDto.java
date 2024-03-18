package kz.sparklab.restnode.dto;


import kz.sparklab.restnode.model.SmartBox;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SmartBoxDto {
    private String name;
    private String type;
    private String address;
    private int height;
    private int width;
    private int length;
    private int sensorHeight;
    private boolean isActive;

    public SmartBoxDto(SmartBox smartBox) {
        this.name = smartBox.getName();
        this.type = smartBox.getType().name();
        this.address = smartBox.getAddress();
        this.width = smartBox.getWidth();
        this.length = smartBox.getLength();
        this.height = smartBox.getHeight();
        this.isActive = smartBox.isActive();
        this.sensorHeight = smartBox.getSensorHeight();
    }

}
