package kz.sparklab.service.impl;

import kz.sparklab.dao.RawDataDAO;
import kz.sparklab.entity.RawData;
import kz.sparklab.service.MainService;
import kz.sparklab.service.ProducerService;
import lombok.extern.log4j.Log4j;
import org.example.dao.AppUserDAO;
import org.example.entity.AppUser;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import static kz.sparklab.service.enums.BotCommands.*;


@Service
@Log4j
public class MainServiceImpl implements MainService {

    private static final String HELP_TEXT = "/get_fullness - for getting fullness of all boxes\n";

    private final RawDataDAO rawDataDAO;
    private final ProducerService producerService;

    public MainServiceImpl(RawDataDAO rawDataDAO, ProducerService producerService) { // AppUserDAO
        this.rawDataDAO = rawDataDAO;
        this.producerService = producerService;
    }


    @Override
    public void processTextMessage(Update update) {
        saveRawData(update);

        var message = update.getMessage();
        var text = message.getText();
        var output = "";

        output = processServiceCommand(text);

        sendAnswer(output, message.getChatId());
    }

    private void sendAnswer(String output, Long chatId) {
        var sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(output)
                .build();

        producerService.produceAnswer(sendMessage);
    }
    private String processServiceCommand(String cmd) {
        if (GET_FULLNESS.equals(cmd)) {
            return "Unavailable now";
        } else if (HELP.equals(cmd)) {
            return help();
        } else if (START.equals(cmd)) {
            return "Привет, что бы посмотреть список команд используй /help";
        } else {
            return "Unknown command";
        }
    }

    private String help() {
        return HELP_TEXT;
    }

    public void saveRawData(Update update) {
        RawData rawData = RawData.builder().event(update).build();

        rawDataDAO.save(rawData);
        log.debug("Update Saved");
    }
}
