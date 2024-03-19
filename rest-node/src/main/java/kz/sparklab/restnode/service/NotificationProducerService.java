package kz.sparklab.restnode.service;

public interface NotificationProducerService {
    void produce(String rabbitQueue, String box_name);
}
