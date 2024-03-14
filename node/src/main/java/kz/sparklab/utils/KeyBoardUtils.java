package kz.sparklab.utils;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class KeyBoardUtils {

    public InlineKeyboardMarkup getInlineKeyboardMarkup(String... buttons) {

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        Arrays.stream(buttons).forEach((String buttonText) -> {
            List<InlineKeyboardButton> row = new ArrayList<>();
            row.add(InlineKeyboardButton.builder().text(buttonText).callbackData(buttonText).build());

            rowList.add(row);
        });

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    public ReplyKeyboardMarkup getReplyKeyBoardMarkup(String... buttons) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();


        List<KeyboardRow> keyboard = new ArrayList<>();
        Arrays.stream(buttons).forEach((String buttonText) -> {
            KeyboardRow row = new KeyboardRow();

            row.add(buttonText);

            keyboard.add(row);
        });

        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setKeyboard(keyboard);

        return keyboardMarkup;
    }
}
