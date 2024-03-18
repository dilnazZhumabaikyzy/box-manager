package kz.sparklab.restnode.exception;

import lombok.extern.log4j.Log4j;

@Log4j
public class BoxCreationError extends RuntimeException {
    public BoxCreationError(String msg) {
        super("Не удается создать бокс. Ошибка: " + msg);
        log.error("FAILED TO CREATE SMARTBOX. CAUSED BY: " + msg);
    }
}
