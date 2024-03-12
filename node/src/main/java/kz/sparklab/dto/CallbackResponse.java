package kz.sparklab.dto;

import lombok.*;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CallbackResponse {
    private AnswerCallbackQuery answerCallbackQuery;
    private SendMessage sendMessage;
}
