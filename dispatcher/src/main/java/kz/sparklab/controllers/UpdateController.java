package kz.sparklab.controllers;

import kz.sparklab.utils.MessageUtils;
import kz.sparklab.service.UpdateProducer;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Controller
@Log4j
public class UpdateController {
    private  TelegramBot telegramBot;
    private final MessageUtils messageUtils;
    private final UpdateProducer updateProducer;


    @Value("${spring.rabbitmq.queues.text-message-update}")
    private String textMessageUpdateQueue;

    @Value("${spring.rabbitmq.queues.doc-message-update}")
    private String docMessageUpdateQueue;

    @Value("${spring.rabbitmq.queues.photo-message-update}")
    private String photoMessageUpdateQueue;

    public UpdateController(MessageUtils messageUtils, UpdateProducer updateProducer) {
            this.messageUtils = messageUtils;
        this.updateProducer = updateProducer;
    }

    public void registerBot(TelegramBot telegramBot) {
       this.telegramBot = telegramBot;
    }

    public void processUpdate(Update update){
        if (update == null){
            log.error("Receive update is null");
            return;
        }

        if (update.hasMessage()) {
            distributeMessagesByType(update);
        } else {
            log.error("Unsupported message type is received: " + update);
        }
    }

    private void distributeMessagesByType(Update update) {
        var message = update.getMessage();
        if (message.hasText()) {
            processTextMessage(update);
        } else if (message.hasDocument()) {
            processDocMessage(update);
        } else if (message.hasPhoto()) {
            processPhotoMessage(update);
        } else {
            setUnsupportedMessageTypeView(update);
        }
    }



    private void processPhotoMessage(Update update) {
        updateProducer.produce(photoMessageUpdateQueue, update);
        setFileIsReceivedView(update);
    }

    private void processDocMessage(Update update) {
        updateProducer.produce(docMessageUpdateQueue, update);
    }

    private void processTextMessage(Update update) {
        updateProducer.produce(textMessageUpdateQueue, update);
    }

    private void setUnsupportedMessageTypeView(Update update) {
        var sendMessage = messageUtils.generateSendMessageWithText(update,
                "Неподдерживаемый тип сообщения!");
        setView(sendMessage);
    }

    private void setFileIsReceivedView(Update update) {
        var sendMessage = messageUtils.generateSendMessageWithText(update,
                "Файл получен! Обрабатывается...");
        setView(sendMessage);
    }

    public void setView(SendMessage sendMessage) {
        telegramBot.sendAnswerMessage(sendMessage);
    }
}
