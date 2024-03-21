package kz.sparklab.restnode.service;

import kz.sparklab.restnode.dto.SensorAnomalyWarningDto;
import kz.sparklab.restnode.model.SensorReport;

public interface SensorAnomalyProducerService {
    void produce(SensorReport sensorReport, long difference);
}
