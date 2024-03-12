package kz.sparklab.service.impl;

import kz.sparklab.controllers.UpdateController;
import kz.sparklab.service.AnswerConsumer;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;


@Service
@Log4j
public class AnswerConsumerImpl implements AnswerConsumer {

    private final UpdateController updateController;

    public AnswerConsumerImpl(UpdateController updateController) {
        this.updateController = updateController;
    }

    @Override
    @RabbitListener(queues = "${spring.rabbitmq.queues.answer-message}")
    public void consume(SendMessage sendMessage) {
        this.updateController.setView(sendMessage);
    }

    @Override
    @RabbitListener(queues = "${spring.rabbitmq.queues.answer-callback-query}")
    public void consumeCallback(AnswerCallbackQuery answerCallbackQuery) {
        this.updateController.setCallbackView(answerCallbackQuery);
    }
}
