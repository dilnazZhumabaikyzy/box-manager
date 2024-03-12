package kz.sparklab.restnode.service.impl;

import kz.sparklab.restnode.mail.EmailRequest;
import kz.sparklab.restnode.service.ConsumerService;
import kz.sparklab.restnode.service.SensorReportService;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

//@Log4j
@Service
public class ConsumerServiceImpl implements ConsumerService {
    private final SensorReportService sensorReportService;

    public ConsumerServiceImpl(SensorReportService sensorReportService) {
        this.sensorReportService = sensorReportService;
    }

    @Override
    @RabbitListener(queues = "sensor_report")
    public void consumeSensorReport(EmailRequest emailRequest) {
//        log.debug("REST-NODE: EmailRequest is received");
        System.out.println("REST-NODE: EmailRequest is received");
        sensorReportService.processInboundEmailReport(emailRequest);
    }
}
