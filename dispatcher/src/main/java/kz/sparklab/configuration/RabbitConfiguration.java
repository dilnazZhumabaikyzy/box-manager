package kz.sparklab.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfiguration {


    @Value("${spring.rabbitmq.queues.text-message-update}")
    private String textMessageUpdateQueue;

    @Value("${spring.rabbitmq.queues.answer-message}")
    private String answerMessageQueue;

    @Value("${spring.rabbitmq.queues.callback-data-update}")
    private String callbackQueryQueue;

    @Value("${spring.rabbitmq.queues.answer-callback-query}")
    private String answerCallbackQueryQueue;
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue textMessageQueue() {
        return new Queue(textMessageUpdateQueue);
    }

    @Bean
    public Queue callbackQueryQueue() {
        return new Queue(callbackQueryQueue);
    }

    @Bean
    public Queue answerMessageQueue() {
        return new Queue(answerMessageQueue);
    }

    @Bean
    public Queue answerCallbackQueryQueue() {
        return new Queue(answerCallbackQueryQueue);
    }
}
