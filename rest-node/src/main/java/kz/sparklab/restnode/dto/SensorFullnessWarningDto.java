package kz.sparklab.restnode.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SensorFullnessWarningDto {
    private Long boxId;
    private String boxName;
    private String address;
    private LocalDateTime lastUpdationDate;
    private int fullnessPercent;
}
