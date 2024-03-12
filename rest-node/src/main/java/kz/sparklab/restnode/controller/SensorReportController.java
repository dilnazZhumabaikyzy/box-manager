package kz.sparklab.restnode.controller;

import kz.sparklab.restnode.mail.EmailRequest;
import kz.sparklab.restnode.service.SensorReportService;
import kz.sparklab.restnode.service.impl.ProducerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sensor")
public class SensorReportController {
    private final SensorReportService sensorReportService;
    private final ProducerService producerService;


    public SensorReportController(SensorReportService sensorReportService, ProducerService producerService) {
        this.sensorReportService = sensorReportService;
        this.producerService = producerService;
    }

    @PostMapping
    public ResponseEntity<Void> testSensorReport(@RequestBody EmailRequest emailRequest){
        producerService.produceAnswer(emailRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
