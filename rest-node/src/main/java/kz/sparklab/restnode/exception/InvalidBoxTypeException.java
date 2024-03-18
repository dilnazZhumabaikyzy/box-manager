package kz.sparklab.restnode.exception;

import kz.sparklab.restnode.model.BoxType;
import lombok.extern.log4j.Log4j;

@Log4j
public class InvalidBoxTypeException extends RuntimeException {
    private static final String VALID_BOX_TYPES = String.join(", ", BoxType.OFFICE.name(), BoxType.STREET.name());

    public InvalidBoxTypeException(){
        super("Неверный тип бокса. Пожалуйста, выберите один из списка:  " + VALID_BOX_TYPES);
        log.error("FAILED DUE TO INVALID BOX TYPE.");
    }
}
