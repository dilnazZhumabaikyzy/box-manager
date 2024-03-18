package kz.sparklab.restnode.controller;

import kz.sparklab.restnode.dto.SensorReportDto;
import kz.sparklab.restnode.model.SensorReport;
import kz.sparklab.restnode.service.SensorReportService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sensor")
public class SensorReportController {
    private final SensorReportService sensorReportService;

    public SensorReportController(SensorReportService sensorReportService) {
        this.sensorReportService = sensorReportService;

    }

    @GetMapping
    public ResponseEntity<Map<String, Integer>> getSensorReport(){
        Map<String, Integer> reports  = sensorReportService.getSensorReport();
        return ResponseEntity.status(HttpStatus.OK).body(reports);
    }

    @GetMapping("all")
    public ResponseEntity< List<SensorReportDto>> getAllSensorReport(){
        List<SensorReportDto> sensorReportDtoList = sensorReportService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(sensorReportDtoList);
    }
    @GetMapping("/id")
    public ResponseEntity<SensorReportDto> getAllSensorReport(@RequestParam String reportId){
        SensorReportDto sensorReportDto = sensorReportService.getSensorReportById(reportId);
        return ResponseEntity.status(HttpStatus.OK).body(sensorReportDto);
    }

    @DeleteMapping("all")
    public ResponseEntity<Void> deleteAll(){
        sensorReportService.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    public ResponseEntity<Void> deleteById(@RequestParam String reportId){
         sensorReportService.delete(reportId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
