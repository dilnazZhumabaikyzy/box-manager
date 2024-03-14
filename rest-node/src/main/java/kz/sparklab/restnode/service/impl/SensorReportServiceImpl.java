package kz.sparklab.restnode.service.impl;

import kz.sparklab.restnode.exception.BoxNotFoundException;
import kz.sparklab.restnode.mail.EmailRequest;
import kz.sparklab.restnode.model.SensorReport;
import kz.sparklab.restnode.model.SmartBox;
import kz.sparklab.restnode.repository.SensorReportRepository;
import kz.sparklab.restnode.repository.SmartBoxRepository;
import kz.sparklab.restnode.service.SensorReportService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
                System.out.println("EMAILREQUEST PROCCESSING");
                System.out.println("box_name: "+emailRequest.getBoxName() + "\nfullness: "+ emailRequest.getFullness() );

                SensorReport sensorReport = SensorReport.builder().box(smartBox).fullness(Double.parseDouble(emailRequest.getFullness())).build();
                sensorReportRepository.save(sensorReport);
    }

    @Override
    public  Map<String, Integer> getSensorReport() {
        List<SensorReport> sensorReportsList = sensorReportRepository.findLatestReportsForEachBox();
        Map<String, Integer> reports = processSensorReports(sensorReportsList);
        return reports;
    }

    private Map<String, Integer> processSensorReports(List<SensorReport> sensorReportsList) {
        List<String> smartBoxNames = smartBoxRepository.findAllBoxNames();

        Map<String, Integer> responseMap = new HashMap<>();

        Set<String> sensorReportNames = sensorReportsList.stream()
                .map(sensorReport -> sensorReport.getBox().getName())
                .collect(Collectors.toSet());

        smartBoxNames.forEach(name -> {
            // If sensor report exists for the smart box
            if (sensorReportNames.contains(name)) {
                SmartBox smartBox = smartBoxRepository.findByName(name)
                        .orElseThrow(BoxNotFoundException::new);

                // Find the matching sensor report
                Optional<SensorReport> optionalSensorReport = sensorReportsList.stream()
                        .filter(sensorReport -> sensorReport.getBox().getName().equals(name))
                        .findFirst();

                if (optionalSensorReport.isPresent()) {
                    SensorReport sensorReport = optionalSensorReport.get();
                    double fullnessInPercentage = (sensorReport.getFullness() / smartBox.getSensorHeight()) * 100;
                    int roundedPercentage = (int) Math.round(fullnessInPercentage);
                    responseMap.put(name, roundedPercentage);
                }
            } else {
                // If no sensor report exists for the smart box, set fullness to 0
                responseMap.put(name, 0);
            }
        });
        return responseMap;
    }

}
