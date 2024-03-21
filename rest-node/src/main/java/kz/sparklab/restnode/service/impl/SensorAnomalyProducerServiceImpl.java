package kz.sparklab.restnode.service.impl;

import kz.sparklab.restnode.dto.SensorAnomalyWarningDto;
import kz.sparklab.restnode.dto.SensorReportDto;
import kz.sparklab.restnode.model.SensorReport;
import kz.sparklab.restnode.model.SmartBox;
import kz.sparklab.restnode.repository.SmartBoxRepository;
import kz.sparklab.restnode.service.SensorAnomalyProducerService;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Log4j
@Service
public class SensorAnomalyProducerServiceImpl implements SensorAnomalyProducerService {
    private final RabbitTemplate rabbitTemplate;
    private final SmartBoxRepository smartBoxRepository;


    public SensorAnomalyProducerServiceImpl(RabbitTemplate rabbitTemplate, SmartBoxRepository smartBoxRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.smartBoxRepository = smartBoxRepository;
    }

    @Override
    public void produce(SensorReport currentReport, long difference) {
        SmartBox anomalyBox = smartBoxRepository.findById(currentReport.getBox().getId()).orElseThrow();

        this.rabbitTemplate.convertAndSend("sensor_anomaly_notification", createAnomalyInfo(anomalyBox, currentReport.getCreatedAt(), difference ));
        log.info("SENSOR ANOMALY NOTIFICATION MESSAGE SENT TO QUEUE. BOX NAME: " + currentReport.getBox().getName());
    }

    private SensorAnomalyWarningDto createAnomalyInfo(SmartBox anomalyBox, Date reportTime, long timeDifferenceMinutes) {
        log.info("Anomaly Detected:" +
                "\nBox ID: " + anomalyBox.getId() +
                "\nBox Name: " + anomalyBox.getName() +
                "\nAddress: " + anomalyBox.getAddress() +
                "\nLast Updation Date: " + reportTime +
                "\nDelay Duration (minutes): " + timeDifferenceMinutes);
        return SensorAnomalyWarningDto.builder().
                boxId(anomalyBox.getId()).
                boxName(anomalyBox.getName()).
                address(anomalyBox.getAddress()).
                lastUpdationDate(reportTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()).
                delayDuration(timeDifferenceMinutes).
                build();
    }
}
