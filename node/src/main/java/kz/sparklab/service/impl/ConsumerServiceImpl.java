package kz.sparklab.service.impl;

import kz.sparklab.service.ConsumerService;
import kz.sparklab.service.MainService;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;


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
    @RabbitListener(queues = "${spring.rabbitmq.queues.doc-message-update}")
    public void consumeDocMessageUpdates(Update update) {
        log.debug("NODE: Doc message is received");

    }

    @Override
    @RabbitListener(queues = "${spring.rabbitmq.queues.photo-message-update}")
    public void consumePhotoMessageUpdates(Update update) {
        log.debug("NODE: Photo message is received");

    }
}
