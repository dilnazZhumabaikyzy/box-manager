package kz.sparklab.restnode.exception;

import lombok.extern.log4j.Log4j;

@Log4j
public class BoxNotFoundException extends RuntimeException{
    public BoxNotFoundException(){
        super("Не удается найти указанный бокс. Пожалуйста, проверьте правильность введенных данных.");
    }
}
