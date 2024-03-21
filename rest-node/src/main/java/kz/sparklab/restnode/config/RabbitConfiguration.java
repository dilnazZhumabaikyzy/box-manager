package kz.sparklab.restnode.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {
    @Value("sensor_fullness_notification")
    private String sensorNotificationQueryQueue;

    @Value("sensor_anomaly_notification")
    private String sensorAnomalyQueryQueue;
    @Bean
    public Queue sensorNotification() {
        return new Queue(sensorNotificationQueryQueue);
    }

    @Bean
    public Queue sensorAnomaly() {
        return new Queue(sensorAnomalyQueryQueue);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
