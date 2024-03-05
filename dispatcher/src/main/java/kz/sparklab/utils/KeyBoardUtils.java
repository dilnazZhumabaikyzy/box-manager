package kz.sparklab.utils;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class KeyBoardUtils {
    
    public InlineKeyboardMarkup getInlineKeyboardMarkup(List<String> keyboard) {
        
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        for (String buttonText : keyboard) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            String callBack = String.format("Button '%s'", buttonText);
            row.add(InlineKeyboardButton.builder().text(buttonText).callbackData(callBack).build());

            rowList.add(row);
        }
        
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }
}
