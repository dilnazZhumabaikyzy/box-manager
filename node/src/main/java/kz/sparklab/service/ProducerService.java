package kz.sparklab.service;

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface ProducerService {
    void produceAnswer(SendMessage sendMessage);
    void produceCallbackAnswer(AnswerCallbackQuery answerCallbackQuery);
}
