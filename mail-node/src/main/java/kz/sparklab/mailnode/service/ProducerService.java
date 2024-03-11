package kz.sparklab.mailnode.service;

import kz.sparklab.mailnode.EmailRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProducerService{
    private String sensorReportQueue = "sensor_report";

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public ProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void produceSensorReport(EmailRequest EmailRequest) {
        System.out.println("EmailRequest-------------->");
        System.out.println(EmailRequest);
        rabbitTemplate.convertAndSend(sensorReportQueue, EmailRequest);
    }
}