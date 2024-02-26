package kz.sparklab.service.enums;

public enum BotCommands {
    GET_FULLNESS("/get_fullness"),
    START("/start"),
    HELP("/help");
    private final String cmd;

    BotCommands(String cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return cmd;
    }

    public boolean equals(String cmd) {
        return this.toString().equals(cmd);
    }
}
