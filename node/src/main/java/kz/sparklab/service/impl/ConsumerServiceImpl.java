package kz.sparklab.service.impl;

import kz.sparklab.dto.SensorAnomalyWarningDto;
import kz.sparklab.dto.SensorFullnessWarningDto;
import kz.sparklab.service.ConsumerService;
import kz.sparklab.service.MainService;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.format.DateTimeFormatter;


@Service
@Log4j
public class ConsumerServiceImpl implements ConsumerService {

    private final MainService mainService;

    public ConsumerServiceImpl(MainService mainService) {
        this.mainService = mainService;
    }

    @Override
    @RabbitListener(queues = "${spring.rabbitmq.queues.text-message-update}")
    public void consumeTextMessageUpdates(Update update) {
        log.debug("NODE: Text message is received");

        mainService.processTextMessage(update);
    }

    @Override
    @RabbitListener(queues = "${spring.rabbitmq.queues.callback-data-update}")
    public void consumeCallbackQueryUpdates(Update update) {
        log.debug("NODE: Text callback is received");

        mainService.processCallbackQuery(update);
    }

    @Override
    @RabbitListener(queues = "sensor_fullness_notification")
    public void consumeNotification(SensorFullnessWarningDto sensorFullnessWarningDto) {
        log.info("REST-NODE: Sensor notification message is received. Box name: " + sensorFullnessWarningDto.getBoxName());
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
            String lastUpdate = dtf.format(sensorFullnessWarningDto.getLastUpdationDate());

            mainService.sendNotifications(
                    String.format("Бокс %s по адресу %s - заполнен!%n" +
                            "Последнее обновление: %s", sensorFullnessWarningDto.getBoxName(), sensorFullnessWarningDto.getAddress(), lastUpdate)
            );
        } catch (Exception e) {
            log.error(e);
        }
    }

    @Override
    @RabbitListener(queues = "sensor_anomaly_notification")
    public void consumeSensorAnomaly(SensorAnomalyWarningDto sensorAnomalyWarningDto) {
        log.info("REST-NODE: Sensor anomaly message is received. Box name: " + sensorAnomalyWarningDto);
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
            String lastUpdate = dtf.format(sensorAnomalyWarningDto.getLastUpdationDate());

            mainService.sendNotifications(
                    String.format("У бокса: %s по адресу %s возможно умеются неполадки!%n" +
                            "Последнее обновление: %s", sensorAnomalyWarningDto.getBoxName(), sensorAnomalyWarningDto.getAddress(), lastUpdate)
            );
        } catch (Exception e) {
            log.error(e);
        }
    }
}
