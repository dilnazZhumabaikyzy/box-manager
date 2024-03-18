package kz.sparklab.service.impl;

import kz.sparklab.dto.CallbackResponse;
import kz.sparklab.service.MainService;
import kz.sparklab.service.ProducerService;
import kz.sparklab.service.RestService;
import kz.sparklab.utils.KeyBoardUtils;
import kz.sparklab.utils.MessageFormatter;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;

import static kz.sparklab.service.enums.BotCallbacks.*;
import static kz.sparklab.service.enums.BotCommands.HELP;
import static kz.sparklab.service.enums.BotCommands.START;


@Service
@Log4j
public class MainServiceImpl implements MainService {

    private static final String HELP_TEXT = "/get_fullness - for getting fullness of all boxes\n";
    private static final String GREETING_TEXT = """
            Приветствую! Я - ваш персональный бот для управления боксами с одеждой благотворительного фонда. 
                                
            Вот мои основные функции:  
                              
            1. **Просмотреть заполненность**: Вы можете узнать текущий процент заполненности боксов в различных точках города, просто отправив мне команду   
                                
            2. **Подписаться на уведомления**: Вы сможете получать сообщения от меня когда какой то из боксов заплниться.
                                
            3. Дополнительные функции (планируются): В будущем будут добавлены функции отчетов и статистики для более детального анализа работы и эффективности процесса сбора одежды.
            """;

    private final ProducerService producerService;
    private final KeyBoardUtils keyBoardUtils;
    private final RestService restService;
    private final MessageFormatter messageFormatter;

    public MainServiceImpl(ProducerService producerService, KeyBoardUtils keyBoardUtils, RestService restService, MessageFormatter messageFormatter) {
        this.producerService = producerService;
        this.keyBoardUtils = keyBoardUtils;
        this.restService = restService;
        this.messageFormatter = messageFormatter;
    }


    @Override
    public void processTextMessage(Update update) {
        var message = update.getMessage();

        var outputMessage = processServiceCommand(message);

        sendAnswer(outputMessage);
    }

    @Override
    public void processCallbackQuery(Update update) {
        var callback = update.getCallbackQuery();

        var outputMessage = processCallbackContent(callback);

        sendCallbackAnswer(outputMessage);
    }

    private SendMessage processServiceCommand(Message message) {
        var text = message.getText();

        if (SHOW_FULLNESS.isEqual(text)) {
            return getFullnessInfo(message);
        } else if(FOLLOW_NOTIFICATIONS.isEqual(text)) {
            return followNotification(message);
        } else if (HELP.equals(text)) {
            return getHelpMessage(message);
        } else if (START.equals(text)) {
            return getGreetingMessage(message);
        } else {
            return getUnavailableCommandMessage(message);
        }
    }

    private CallbackResponse processCallbackContent(CallbackQuery callback) {
        var callbackData = callback.getData();

        if (SHOW_ALL.isEqual(callbackData)) {
            return getFullnessInfo(callback);
        } else if (SHOW_ONLY_RED.isEqual(callbackData)
                || SHOW_ONLY_YELLOW.isEqual(callbackData)
                || SHOW_ONLY_GREEN.isEqual(callbackData)) {
            return getFullnessInfo(callbackData, callback);
        }

        return CallbackResponse.builder()
                .answerCallbackQuery(
                        AnswerCallbackQuery.builder()
                                .callbackQueryId(callback.getId())
                                .text("Неизвестная команда")
                                .build()
                )
                .build();
    }

    private SendMessage followNotification(Message message) {
        return SendMessage.builder()
                .text("Временно Недоступно :(")
                .chatId(message.getChatId())
                .build();
    }

    private CallbackResponse getFullnessInfo(String color, CallbackQuery callback) {

        HashMap<String, Integer> map = restService.getFullnessFromAPI();

        AnswerCallbackQuery answerCallbackQuery = AnswerCallbackQuery.builder()
                .callbackQueryId(callback.getId())
                .text(color)
                .build();

        SendMessage sendMessage = SendMessage.builder()
                .text(messageFormatter.getFilteredFullnessMessage(color, map))
                .chatId(callback.getMessage().getChatId())
                .build();

        return CallbackResponse.builder()
                .answerCallbackQuery(answerCallbackQuery)
                .sendMessage(sendMessage)
                .build();
    }

    private CallbackResponse getFullnessInfo(CallbackQuery callback) {

        HashMap<String, Integer> map = restService.getFullnessFromAPI();


        AnswerCallbackQuery answerCallbackQuery = AnswerCallbackQuery.builder()
                .callbackQueryId(callback.getId())
                .text(SHOW_ALL.toString())
                .build();

        SendMessage sendMessage = SendMessage.builder()
                .text(messageFormatter.getFullnessMessage(map))
                .chatId(callback.getMessage().getChatId())
                .build();

        return CallbackResponse.builder()
                .answerCallbackQuery(answerCallbackQuery)
                .sendMessage(sendMessage)
                .build();
    }

    private SendMessage getFullnessInfo(Message message) {

        return SendMessage.builder()
                .chatId(message.getChatId())
                .text("Выберите один из опций: ")
                .replyMarkup(keyBoardUtils.getInlineKeyboardMarkup(
                        SHOW_ALL.toString(),
                        SHOW_ONLY_RED.toString(),
                        SHOW_ONLY_YELLOW.toString(),
                        SHOW_ONLY_GREEN.toString()))
                .build();
    }

    private SendMessage getUnavailableCommandMessage(Message message) {
        return SendMessage.builder()
                .text("Неизвестная команда")
                .chatId(message.getChatId())
                .build();
    }

    private SendMessage getHelpMessage(Message message) {
        return SendMessage.builder()
                .text(HELP_TEXT)
                .chatId(message.getChatId())
                .build();
    }

    private SendMessage getGreetingMessage(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text(GREETING_TEXT)
                .replyMarkup(keyBoardUtils.getReplyKeyBoardMarkup(
                        SHOW_FULLNESS.toString(),
                        FOLLOW_NOTIFICATIONS.toString()))
                .build();


    }

    private void sendAnswer(SendMessage sendMessage) {
        producerService.produceAnswer(sendMessage);
    }
    private void sendCallbackAnswer(CallbackResponse callbackResponse) {
        if (callbackResponse.getAnswerCallbackQuery() != null)
            producerService.produceCallbackAnswer(callbackResponse.getAnswerCallbackQuery());

        if (callbackResponse.getSendMessage() != null)
            producerService.produceAnswer(callbackResponse.getSendMessage());
    }
}
