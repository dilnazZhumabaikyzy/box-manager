package kz.sparklab.restnode.dto;

import jakarta.persistence.*;
import kz.sparklab.restnode.model.SensorReport;
import kz.sparklab.restnode.model.SmartBox;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class SensorReportDto {
    private Long id;
    private Long box_id;
    private double fullness;
    private Date createdAt;

    public SensorReportDto(SensorReport sensorReport){
        this.id = sensorReport.getId();
        this.box_id = sensorReport.getBox().getId();
        this.fullness = sensorReport.getFullness();
        this.createdAt = sensorReport.getCreatedAt();
    }
}
