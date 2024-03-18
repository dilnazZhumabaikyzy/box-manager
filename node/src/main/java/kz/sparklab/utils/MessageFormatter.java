   package kz.sparklab.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Component
public class MessageFormatter {

    public String getFullnessMessage(Map<String, Integer> map) {
        StringBuilder message = new StringBuilder();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

        message.append("Заполненность на ")
                .append(dtf.format(now));

        map.forEach((key, value) -> message.append(key)
                .append("\n")
                .append(getFullnessColorEmoji(value))
                .append(" ")
                .append(value)
                .append("%\n"));

        return message.toString();
    }

    private String getFullnessColorEmoji(Integer percent) {
        StringBuilder color = new StringBuilder();
        if (percent > 70) {
            for (int i = 0; i < percent / 10; i++) {
                color.append("🟥").append(" ");
            }
        } else if (percent > 40) {
            for (int i = 0; i < percent / 10; i++) {
                color.append("🟨").append(" ");
            }
        } else if (percent > 10){
            for (int i = 0; i < percent / 10; i++) {
                color.append("🟩").append(" ");
            }
        } else {
            color.append("🟩").append(" ");
        }

        return color.toString();
    }

}
