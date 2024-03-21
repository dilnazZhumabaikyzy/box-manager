package kz.sparklab.restnode.service.impl;

import kz.sparklab.restnode.dto.SensorFullnessWarningDto;
import kz.sparklab.restnode.service.SensorFullnessWarningProducerService;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Log4j
@Service
public class SensorFullnessWarningProducerServiceImpl implements SensorFullnessWarningProducerService {
    private final RabbitTemplate rabbitTemplate;

    public SensorFullnessWarningProducerServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void produce(String rabbitQueue, SensorFullnessWarningDto sensorFullnessWarningDto) {
        this.rabbitTemplate.convertAndSend(rabbitQueue, sensorFullnessWarningDto);
        log.info("NOTIFICATION MESSAGE SENT TO QUEUE. BOX NAME: " + sensorFullnessWarningDto);
    }

}
