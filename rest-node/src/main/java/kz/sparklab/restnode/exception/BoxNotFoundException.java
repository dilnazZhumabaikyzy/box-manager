package kz.sparklab.restnode.exception;

public class BoxNotFoundException extends RuntimeException{
    public BoxNotFoundException(){
        super("Не удается найти указанный бокс. Пожалуйста, проверьте правильность введенных данных.");
    }
}
