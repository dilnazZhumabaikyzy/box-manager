package kz.sparklab.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import static kz.sparklab.service.enums.BotCallbacks.*;

@Component
public class MessageFormatter {

    public String getFullnessMessage(Map<String, Integer> map) {
        if (map.isEmpty())
            return "Список пуст \uD83E\uDED7";

        StringBuilder message = new StringBuilder();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

        message.append("Заполненность на ")
                .append(dtf.format(now))
                .append("\n\n");

        map.forEach((key, value) -> message.append(key)
                .append("\n")
                .append(getFullnessColorEmoji(value))
                .append(" ")
                .append(value)
                .append("%\n\n")
        );

        return message.toString();
    }

    private String getFullnessColorEmoji(Integer percent) {
        StringBuilder color = new StringBuilder();
        if (percent >= 70) {
            for (int i = 0; i < percent / 10; i++) {
                color.append("🟥").append(" ");
            }
        } else if (percent >= 40) {
            for (int i = 0; i < percent / 10; i++) {
                color.append("🟨").append(" ");
            }
        } else if (percent >= 10) {
            for (int i = 0; i < percent / 10; i++) {
                color.append("🟩").append(" ");
            }
        } else {
            color.append("🟩").append(" ");
        }

        return color.toString();
    }

    public String getFilteredFullnessMessage(String color, Map<String, Integer> map) {
        if (map.isEmpty())
            return "Список пуст \uD83E\uDED7";

        StringBuilder message = new StringBuilder();
        List<Map.Entry<String, Integer>> list = null;


        if (SHOW_ONLY_RED.isEqual(color)) {
            message.append(SHOW_ONLY_RED);
            list = map.entrySet().stream().filter(box -> box.getValue() >= 70).toList();
        } else if (SHOW_ONLY_YELLOW.isEqual(color)) {
            message.append(SHOW_ONLY_YELLOW);
            list = map.entrySet().stream().filter(box -> box.getValue() >= 40 && box.getValue() < 70).toList();
        } else if (SHOW_ONLY_GREEN.isEqual(color)) {
            message.append(SHOW_ONLY_GREEN);
            list = map.entrySet().stream().filter(box -> box.getValue() >= 0 && box.getValue() < 40).toList();
        }

        assert list != null;

        message.append(":\n\n");

        if (list.isEmpty()) {
            return message.append("Список пуст \uD83E\uDED7").toString();
        }
        list.forEach(box ->
                message.append(box.getKey())
                        .append("\n")
                        .append(getFullnessColorEmoji(box.getValue()))
                        .append(" ")
                        .append(box.getValue())
                        .append("%\n\n")
        );

        return message.toString();
    }
}
