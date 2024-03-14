package kz.sparklab.restnode.service.impl;

import kz.sparklab.restnode.exception.BoxNotFoundException;
import kz.sparklab.restnode.mail.EmailRequest;
import kz.sparklab.restnode.service.ConsumerService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

//@Log4j
@Service
public class ConsumerServiceImpl implements ConsumerService {
    private final SensorReportServiceImpl sensorReportServiceImpl;

    public ConsumerServiceImpl(SensorReportServiceImpl sensorReportServiceImpl) {
        this.sensorReportServiceImpl = sensorReportServiceImpl;
    }

    @Override
    @RabbitListener(queues = "sensor_report")
    public void consumeSensorReport(EmailRequest emailRequest) {
        System.out.println("REST-NODE: EmailRequest is received");
        try {
            sensorReportServiceImpl.processInboundEmailReport(emailRequest);
        } catch (BoxNotFoundException e) {
            // Log the error or handle it gracefully
            System.err.println("Box not found: " + e.getMessage());
            // Optionally, you can return an error response
        }
        catch (Exception e) {
            // Log the error or handle it gracefully
            System.err.println("Error: " + e.getMessage());
            // Optionally, you can return an error response
        }
    }
}
