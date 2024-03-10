package kz.sparklab.restnode.exception;

public class SmartBoxNotFoundException extends RuntimeException{
    public SmartBoxNotFoundException(){
        super("Не удается найти указанный smart-бокс. Пожалуйста, проверьте правильность введенных данных.");
    }
}