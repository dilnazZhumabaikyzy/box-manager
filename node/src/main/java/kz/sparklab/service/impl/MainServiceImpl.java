package kz.sparklab.service.impl;

import kz.sparklab.service.MainService;
import kz.sparklab.service.ProducerService;
import kz.sparklab.utils.KeyBoardUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static kz.sparklab.service.enums.BotCommands.*;
import static kz.sparklab.service.enums.BotCallbacks.*;


@Service
@Log4j
public class MainServiceImpl implements MainService {

    private static final String HELP_TEXT = "/get_fullness - for getting fullness of all boxes\n";
    private static final String GREETING_TEXT = """
                    Приветствую! Я - ваш персональный бот для управления боксами с одеждой благотворительного фонда. 
                    
                    Вот мои основные функции:  
                  
                    1. Просмотреть заполненность боксов: Вы можете узнать текущий процент заполненности боксов в различных точках города, просто отправив мне команду   
                    
                    2. Подписаться на уведомления: Вы сможете получать сообщения от меня когда какой то из боксов заплниться.
                    
                    3. Дополнительные функции (планируются): В будущем будут добавлены функции отчетов и статистики для более детального анализа работы и эффективности процесса сбора одежды.
                    """;
    private static final String FULLNESS_EXAMPLE = """
                1. Достык Плаза                          \s
                    🟥 🟥 🟥 🟥 🟥 🟥 🟥 🟥 🟥 🟥  97%
                                
                2. Астана Тауэрс                         \s
                    🟥 🟥 🟥 🟥 🟥 🟥 🟥 🟥 🟥  86%
                                
                3. Байтерек                              \s
                    🟥 🟥 🟥 🟥 🟥 🟥 🟥 🟥  80%
                                
                4. Музей                                 \s
                    🟨 🟨 🟨 🟨 🟨 🟨 🟨  75%
                                
                5. Хан Шатыр                             \s
                    🟩 🟩 🟩  35%
                                
                6. Хан Шатыр                             \s
                    🟩 🟩 🟩  35%
                """;

    private final ProducerService producerService;
    private final KeyBoardUtils keyBoardUtils;

    public MainServiceImpl(ProducerService producerService, KeyBoardUtils keyBoardUtils) {
        this.producerService = producerService;
        this.keyBoardUtils = keyBoardUtils;
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

        sendAnswer(outputMessage);
    }

    private SendMessage processCallbackContent(CallbackQuery callback) {
        var callbackData = callback.getData();

        if (SHOW_FULLNESS.isEqual(callbackData)) {
            return getFullnessInfo(callback);
        } else if(FOLLOW_NOTIFICATIONS.isEqual(callbackData)) {
            return followNotification(callback);
        }

        return SendMessage.builder()
                .text("Неизвестная команда")
                .chatId(callback.getMessage().getChatId())
                .build() ;
    }

    private SendMessage followNotification(CallbackQuery callback) {
        return SendMessage.builder()
                .text("Временно Недоступно")
                .chatId(callback.getMessage().getChatId())
                .build();
    }

    private SendMessage getFullnessInfo(CallbackQuery callback) {
        return SendMessage.builder()
                .chatId(callback.getMessage().getChatId())
                .text(FULLNESS_EXAMPLE)
                .build();
    }

    private SendMessage processServiceCommand(Message message) {
        var text = message.getText();

        if (GET_FULLNESS.equals(text)) {
            return getUnavailableCommandMessage(message);
        } else if (HELP.equals(text)) {
            return getHelpMessage(message);
        } else if (START.equals(text)) {
            return getGreetingMessage(message);
        } else {
            return getUnavailableCommandMessage(message);
        }
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
                .replyMarkup(keyBoardUtils.getInlineKeyboardMarkup("Посмотреть заполненность",
                        "Подписаться на уведомления"))
                .build();
    }

    private void sendAnswer(SendMessage sendMessage) {
        producerService.produceAnswer(sendMessage);
    }
}
