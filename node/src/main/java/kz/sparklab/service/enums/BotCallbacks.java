package kz.sparklab.service.enums;

public enum BotCallbacks {

    SHOW_FULLNESS("Посмотреть заполненность \uD83D\uDC40"),
    FOLLOW_NOTIFICATIONS("Подписаться на уведомления \uD83D\uDD14"),
    SHOW_ONLY_GREEN("Показать только \uD83D\uDFE9"),
    SHOW_ONLY_YELLOW("Показать только \uD83D\uDFE8"),
    SHOW_ONLY_RED("Показать только \uD83D\uDFE5");


    private final String cmd;
    BotCallbacks(String cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return cmd;
    }

    public boolean isEqual(String cmd) {
        return this.toString().equals(cmd);
    }
}
