package kz.sparklab.restnode.service.impl;

import kz.sparklab.restnode.dto.SensorReportDto;
import kz.sparklab.restnode.exception.BoxNotFoundException;
import kz.sparklab.restnode.exception.DeletionFailedException;
import kz.sparklab.restnode.exception.SensorReportCreationException;
import kz.sparklab.restnode.mail.EmailRequest;
import kz.sparklab.restnode.model.SensorReport;
import kz.sparklab.restnode.model.SmartBox;
import kz.sparklab.restnode.repository.SensorReportRepository;
import kz.sparklab.restnode.repository.SmartBoxRepository;
import kz.sparklab.restnode.service.SensorReportService;
import lombok.extern.log4j.Log4j;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Log4j
@Service
public class SensorReportServiceImpl implements SensorReportService {
    private final SensorReportRepository sensorReportRepository;
    private final SmartBoxRepository smartBoxRepository;

    public SensorReportServiceImpl(SensorReportRepository sensorReportRepository, SmartBoxRepository smartBoxRepository) {
        this.sensorReportRepository = sensorReportRepository;
        this.smartBoxRepository = smartBoxRepository;
    }

    @Override
    public void processInboundEmailReport(EmailRequest emailRequest){
                SmartBox smartBox = smartBoxRepository.findByName(emailRequest.getBoxName()).orElseThrow(BoxNotFoundException::new);
                log.info(String.format("EMAIL REQUEST PROCESSING: Box Name: {%s}, Fullness: {%s}", emailRequest.getBoxName(), emailRequest.getFullness()));

                SensorReport sensorReport = SensorReport.builder().box(smartBox).fullness(Double.parseDouble(emailRequest.getFullness())).build();
                try {
                    sensorReportRepository.save(sensorReport);
                    log.info("SENSOR REPORT CREATED SUCCESSFUL");
                } catch (Exception e) {
                    throw new SensorReportCreationException(e.getMessage());
                }
    }

    @Override
    public  Map<String, Integer> getSensorReport() {
        List<SensorReport> sensorReportsList = sensorReportRepository.findLatestReportsForEachBox();
        return processSensorReports(sensorReportsList);
    }

    @Override
    public List<SensorReportDto> getAll() {
        List<SensorReport> sensorReportList = sensorReportRepository.findAll();
        return converSensorReportEntityListToDtoList(sensorReportList);
    }

    private List<SensorReportDto> converSensorReportEntityListToDtoList(List<SensorReport> sensorReportList) {
        List<SensorReportDto> dtoList = new ArrayList<>();
        sensorReportList.forEach(sensorReport -> {
            dtoList.add(convertSensorReportEntityToDto(sensorReport));
        });
        return dtoList;
    }

    @Override
    public SensorReportDto getSensorReportById(String reportId) {
        SensorReport sensorReport = sensorReportRepository.findById(Long.valueOf(reportId)).orElseThrow();
        return convertSensorReportEntityToDto(sensorReport);
    }

    private SensorReportDto convertSensorReportEntityToDto(SensorReport sensorReport) {
        return new SensorReportDto(sensorReport);
    }

    @Override
    public void deleteAll() {
        try {
            sensorReportRepository.deleteAll();
            log.info("DELETION OF ALL SENSOR REPORTS SUCCESSFUL");
        } catch (Exception e) {
            throw new DeletionFailedException(e.getMessage());
        }

    }

    @Override
    public void delete(String reportId) {
        SensorReport existingSensorReport = sensorReportRepository.findById(Long.valueOf(reportId)).orElseThrow();

        try {
            sensorReportRepository.delete(existingSensorReport);
            log.info("SENSOR REPORT DELETED SUCCESSFULLY. ID: " + reportId);

        }
        catch (DeletionFailedException e){
            throw new DeletionFailedException(e.getMessage());
        }
    }


    private Map<String, Integer> processSensorReports(List<SensorReport> sensorReportsList) {
        List<Long> smartBoxIds = smartBoxRepository.getAllBoxIds();

        Map<String, Integer> responseMap = new HashMap<>();

        Set<Long> sensorReportIds= sensorReportsList.stream()
                .map(sensorReport -> sensorReport.getBox().getId())
                .collect(Collectors.toSet());

        smartBoxIds.forEach(id -> {
            // If sensor report exists for the smart box
            if (sensorReportIds.contains(id)) {
                SmartBox smartBox = smartBoxRepository.findById(id)
                        .orElseThrow(BoxNotFoundException::new);

                // Find the matching sensor report
                Optional<SensorReport> optionalSensorReport = sensorReportsList.stream()
                        .filter(sensorReport -> sensorReport.getBox().getId().equals(id))
                        .findFirst();

                if (optionalSensorReport.isPresent()) {
                    SensorReport sensorReport = optionalSensorReport.get();
                    double fullnessInPercentage = ((smartBox.getHeight() - sensorReport.getFullness()) / smartBox.getSensorHeight()) * 100;
                    int roundedPercentage = (int) Math.round(fullnessInPercentage);
                    responseMap.put(id.toString(), roundedPercentage);
                }
            } else {
                // If no sensor report exists for the smart box, set fullness to 0
                responseMap.put(id.toString(), 0);
            }
        });
        return responseMap;
    }

}
