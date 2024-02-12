package kz.sparklab;


import kz.sparklab.controllers.TelegramBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@SpringBootApplication
public class DispatcherApplication implements CommandLineRunner{

    private final
    TelegramBot telegramBot;

    public DispatcherApplication(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public static void main(String[] args) {
        SpringApplication.run(DispatcherApplication.class);
    }
    @Override
    public void run(String... arg0) throws TelegramApiException {
        telegramBot.botConnect();
    }
}
