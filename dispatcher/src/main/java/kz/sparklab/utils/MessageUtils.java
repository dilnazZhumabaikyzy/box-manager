package kz.sparklab.utils;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;


@Component
public class MessageUtils {
    public  SendMessage generateSendMessageWithText(Update update, String text){
        var message = update.getMessage();
        var sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);

        return sendMessage;
    }
    public SendMessage generateSendMessageWithKeyboard(Update update, String text, InlineKeyboardMarkup keyboard) {
        var message = update.getMessage();
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text(text).replyMarkup(keyboard)
                .build();    }
}
