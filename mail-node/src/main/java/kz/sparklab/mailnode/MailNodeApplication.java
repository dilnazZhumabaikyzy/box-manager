package kz.sparklab.mailnode;

import kz.sparklab.mailnode.config.EmailConfiguration;
import kz.sparklab.mailnode.config.RabbitConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class MailNodeApplication implements CommandLineRunner{

    public static void main(String[] args) {
        SpringApplication.run(MailNodeApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(RabbitConfiguration.class, EmailConfiguration.class);
        EmailListener emailListener = context.getBean(EmailListener.class);
        emailListener.startListening();
    }
}
