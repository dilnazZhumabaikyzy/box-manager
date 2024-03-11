package kz.sparklab.restnode.config;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {


    @Value("${spring.rabbitmq.queues.sensor-report}")
    private String sensorReportQueue;

    @Bean
    public Queue sensorReportQueue() {
        return new Queue(sensorReportQueue);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
