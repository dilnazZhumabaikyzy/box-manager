package kz.sparklab.restnode.dto;

import kz.sparklab.restnode.model.Box;
import kz.sparklab.restnode.model.SmartBox;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SmartBoxDto{
    private int sensorHeight;
    private boolean isActive;

    public SmartBoxDto(SmartBox smartBox) {
        this.isActive = smartBox.isActive();
        this.sensorHeight = smartBox.getSensorHeight();
    }


}
