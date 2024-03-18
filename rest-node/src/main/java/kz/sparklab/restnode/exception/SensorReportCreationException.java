package kz.sparklab.restnode.exception;

import lombok.extern.log4j.Log4j;

@Log4j
public class SensorReportCreationException extends RuntimeException {
    public SensorReportCreationException(String message) {
        super("Не удалось создать отчет заполненности. Ошибка: " + message);
        log.error("SENSOR REPORT FAILED. CAUSED BY: " +  message);
    }
}
