package kz.sparklab.restnode.scheduled;

import kz.sparklab.restnode.dto.SensorAnomalyWarningDto;
import kz.sparklab.restnode.model.SensorReport;
import kz.sparklab.restnode.model.SmartBox;
import kz.sparklab.restnode.repository.SensorReportRepository;
import kz.sparklab.restnode.repository.SmartBoxRepository;
import kz.sparklab.restnode.service.SensorAnomalyProducerService;
import lombok.extern.log4j.Log4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.time.Duration;
@Log4j
@Component
public class ScheduledTask {

    private final SensorReportRepository sensorReportRepository;

    private final SensorAnomalyProducerService sensorAnomalyProducerService;

    public ScheduledTask(SensorReportRepository sensorReportRepository, SensorAnomalyProducerService sensorAnomalyProducerService) {
        this.sensorReportRepository = sensorReportRepository;
        this.sensorAnomalyProducerService = sensorAnomalyProducerService;
    }

//    @Scheduled(cron = "0 */15 * * * *")

    @Scheduled(cron = "0 */1 * * * *") // Run every minute
    public void checkSensorsState() {
        log.info("SENSOR DIAGNOSTICS");
        List<SensorReport> sensorLastReports = sensorReportRepository.findLatestReportsForEachBox();
        sensorLastReports.forEach(sensorReport -> {
            log.info("BOX ID: " + sensorReport.getBox().getId() + ". " + "CREATED AT: " + sensorReport.getCreatedAt());
        });


        for (int i = 0; i < sensorLastReports.size() - 1; i++) {
            SensorReport currentReport = sensorLastReports.get(i);
            LocalDateTime currentTime = LocalDateTime.now();
            LocalDateTime reportTime = currentReport.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            // Calculate the time difference
            Duration duration = Duration.between(reportTime, currentTime);

            // Convert duration to minutes
            long timeDifferenceMinutes = duration.toMinutes();

            // Check if the time difference is greater than 30 minutes
            if (timeDifferenceMinutes > 30) {
                sensorAnomalyProducerService.produce(currentReport, timeDifferenceMinutes);
            }
        }

    }
}
