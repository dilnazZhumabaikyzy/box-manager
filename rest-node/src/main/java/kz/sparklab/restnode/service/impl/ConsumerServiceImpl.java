package kz.sparklab.restnode.service.impl;

import kz.sparklab.restnode.exception.BoxNotFoundException;
import kz.sparklab.restnode.mail.EmailRequest;
import kz.sparklab.restnode.service.ConsumerService;
import kz.sparklab.restnode.service.SensorReportService;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Log4j
@Service
public class ConsumerServiceImpl implements ConsumerService {
    private final SensorReportService sensorReportService;

    public ConsumerServiceImpl(SensorReportService sensorReportService) {
        this.sensorReportService = sensorReportService;
    }

    @Override
    @RabbitListener(queues = "sensor_report")
    public void consumeSensorReport(EmailRequest emailRequest) {
        log.info("REST-NODE: EmailRequest is received. Message content: " + emailRequest);
        try {
            sensorReportService.processInboundEmailReport(emailRequest);
        } catch (BoxNotFoundException e) {
            log.error("FAILED TO PROCESS QUEUE: BOX NOT FOUND EXCEPTION OCCURRED - " + e.getMessage());
        } catch (Exception e) {
            log.error("FAILED TO PROCESS QUEUE: AN ERROR OCCURRED - " + e.getMessage());
        }
    }
}
