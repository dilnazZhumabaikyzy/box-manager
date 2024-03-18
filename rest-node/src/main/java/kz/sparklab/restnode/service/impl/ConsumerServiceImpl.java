package kz.sparklab.restnode.service.impl;

import kz.sparklab.restnode.exception.BoxNotFoundException;
import kz.sparklab.restnode.mail.EmailRequest;
import kz.sparklab.restnode.service.ConsumerService;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Log4j
public class ConsumerServiceImpl implements ConsumerService {
    private final SensorReportServiceImpl sensorReportServiceImpl;

    public ConsumerServiceImpl(SensorReportServiceImpl sensorReportServiceImpl) {
        this.sensorReportServiceImpl = sensorReportServiceImpl;
    }

    @Override
    @RabbitListener(queues = "sensor_report")
    public void consumeSensorReport(EmailRequest emailRequest) {
        log.info("REST-NODE: EmailRequest is received");
        try {
            sensorReportServiceImpl.processInboundEmailReport(emailRequest);
        } catch (BoxNotFoundException e) {
            log.info("Box not found: " + e.getMessage());
        }
        catch (Exception e) {
            log.info("Error: " + e.getMessage());
        }
    }
}
