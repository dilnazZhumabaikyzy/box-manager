package kz.sparklab.mailnode.config;

import kz.sparklab.mailnode.EmailListener;
import kz.sparklab.mailnode.service.ProducerService;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.mail.*;
import java.util.Properties;


@Configuration
public class EmailConfiguration {


//  @Value("${email.host}")
    private String emailHost = "imap.gmail.com";

//    @Value("${email.port}")
    private String emailPort = "993";

//    @Value("${email.username}")
    private String emailUsername = "aestgreat@gmail.com";

//    @Value("${email.password}")
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
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("userok");
        connectionFactory.setPassword("p@ssw0rd");
        return connectionFactory;
    }
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory()); // Set ConnectionFactory
        return rabbitTemplate;
    }

    @Bean
    public ProducerService producerService() {
        return new ProducerService(rabbitTemplate());
    }

    @Bean
    public EmailListener emailListener() {
        return new EmailListener(mailSession(), emailUsername, emailPassword, producerService());
    }
}