package kz.sparklab.restnode.service.impl;

import kz.sparklab.restnode.service.NotificationProducerService;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Log4j
@Service
public class NotificationProducerServiceImpl implements NotificationProducerService {
    private final RabbitTemplate rabbitTemplate;

    public NotificationProducerServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void produce(String rabbitQueue, String boxName) {
        this.rabbitTemplate.convertAndSend(rabbitQueue, boxName);
        log.info("NOTIFICATION MESSAGE SENT TO QUEUE. BOX NAME: " + boxName);
    }

}
