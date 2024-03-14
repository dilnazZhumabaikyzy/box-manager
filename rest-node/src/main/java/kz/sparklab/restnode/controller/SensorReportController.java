package kz.sparklab.restnode.controller;

import kz.sparklab.restnode.mail.EmailRequest;
import kz.sparklab.restnode.service.impl.SensorReportServiceImpl;
//import kz.sparklab.restnode.service.impl.ProducerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("sensor")
public class SensorReportController {
    private final SensorReportServiceImpl sensorReportServiceImpl;
//    private final ProducerService producerService;


    public SensorReportController(SensorReportServiceImpl sensorReportServiceImpl) {
        this.sensorReportServiceImpl = sensorReportServiceImpl;

    }

    @PostMapping
    public ResponseEntity<Void> testSensorReport(@RequestBody EmailRequest emailRequest){
    //    producerService.produceAnswer(emailRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    public ResponseEntity<Map<String, Integer>> getSensorReport(){
        Map<String, Integer> reports  = sensorReportServiceImpl.getSensorReport();
        return ResponseEntity.status(HttpStatus.OK).body(reports);
    }
}
