package kz.sparklab.mailnode.config;

import kz.sparklab.mailnode.EmailListener;
import kz.sparklab.mailnode.service.ProduceMessageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.mail.*;
import java.util.Properties;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;




@Configuration
public class EmailConfiguration {
    private String emailHost = "imap.gmail.com";
    private String emailPort = "993";
    private String emailUsername = "aestgreat@gmail.com";
    private String emailPassword = "qxerpvuyznnzatdi";

    @Bean
    public Session mailSession() {
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        props.setProperty("mail.imaps.host", emailHost);
        props.setProperty("mail.imaps.port", emailPort);

        // Create a new session with the properties
        Session session = Session.getInstance(props);
        session.setDebug(true); // Enable debug mode for troubleshooting

        return session;
    }


    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setUsername("userok");
        connectionFactory.setPassword("p@ssw0rd");
     //   connectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CONNECTION);
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public ProduceMessageService produceMessageService() {
        return new ProduceMessageService(rabbitTemplate());
    }
    @Bean
    public EmailListener emailListener() {
        return new EmailListener(mailSession(), emailUsername, emailPassword, produceMessageService());
    }
}