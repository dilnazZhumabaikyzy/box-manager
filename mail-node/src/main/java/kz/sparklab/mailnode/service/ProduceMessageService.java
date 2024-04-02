package kz.sparklab.mailnode.service;

import kz.sparklab.mailnode.EmailRequest;
import kz.sparklab.mailnode.config.RabbitConfiguration;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProduceMessageService {

    private final RabbitTemplate rabbitTemplate;

    public ProduceMessageService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void produceMessage(EmailRequest emailRequest) {
        System.out.println("Sending to queue");
        rabbitTemplate.convertAndSend(RabbitConfiguration.EXCHANGE_NAME, "myRoutingKey.messages",
                emailRequest);
    }
}