package kz.sparklab.restnode.service;

import kz.sparklab.restnode.dto.SensorFullnessWarningDto;

public interface SensorFullnessWarningProducerService {
    void produce(String rabbitQueue, SensorFullnessWarningDto sensorFullnessWarningDto);
}
