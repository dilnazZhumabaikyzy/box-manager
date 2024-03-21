package kz.sparklab.restnode.service;

import kz.sparklab.restnode.dto.SensorAnomalyWarningDto;
import kz.sparklab.restnode.mail.EmailRequest;


public interface ConsumerService {
    void consumeSensorReport(EmailRequest emailRequest);
    void consumeNotification(String boxName);
    void consumeSensorAnomaly(SensorAnomalyWarningDto sensorAnomalyWarningDto);
}
