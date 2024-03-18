package kz.sparklab.restnode.exception;

import lombok.extern.log4j.Log4j;

@Log4j
public class DeletionFailedException extends RuntimeException {
    public DeletionFailedException(String message){
        super("Не удалось удалить. Ошибка: " + message);
        log.error("FAILED TO DELETE. CAUSED BY : " + message);
    }
}
