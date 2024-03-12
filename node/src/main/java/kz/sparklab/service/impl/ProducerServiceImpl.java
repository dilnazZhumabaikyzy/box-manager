package kz.sparklab.service.impl;

import kz.sparklab.service.ProducerService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;


@Service
public class ProducerServiceImpl implements ProducerService {

    @Value("${spring.rabbitmq.queues.answer-message}")
    private String answerMessageQueue;

    @Value("${spring.rabbitmq.queues.answer-callback-query}")
    private String answerCallbackQueryQueue;

    private final RabbitTemplate rabbitTemplate;

    public ProducerServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void produceAnswer(SendMessage sendMessage) {
        rabbitTemplate.convertAndSend(answerMessageQueue, sendMessage);
    }

    @Override
    public void produceCallbackAnswer(AnswerCallbackQuery answerCallbackQuery) {
        rabbitTemplate.convertAndSend(answerCallbackQueryQueue, answerCallbackQuery);
    }
}
