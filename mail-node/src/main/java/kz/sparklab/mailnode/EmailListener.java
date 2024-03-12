package kz.sparklab.mailnode;

import jakarta.mail.*;


import com.sun.mail.imap.IMAPFolder;
import jakarta.mail.event.MessageCountAdapter;
import jakarta.mail.event.MessageCountEvent;
import kz.sparklab.mailnode.service.ProduceMessageService;

import java.io.IOException;
public class EmailListener extends MessageCountAdapter {
    private Session session;
    private String username;
    private String password;

    private ProduceMessageService produceMessageService;



    public EmailListener(Session session, String username, String password, ProduceMessageService produceMessageService) {
        this.session = session;
        this.username = username;
        this.password = password;
        this.produceMessageService = produceMessageService;
    }

    public void startListening() throws MessagingException, InterruptedException, IOException {
        Store store = session.getStore("imaps");
        store.connect(username, password);



        IMAPFolder inbox = (IMAPFolder)store.getFolder("INBOX");
        inbox.open(Folder.READ_WRITE);

        // Create a new thread to keep the connection alive
        Thread keepAliveThread = new Thread(new KeepAliveRunnable(inbox), "IdleConnectionKeepAlive");
        keepAliveThread.start();

        inbox.addMessageCountListener(new MessageCountAdapter() {
            @Override
            public void messagesAdded(MessageCountEvent event) {
                // Process the newly added messages
                Message[] messages = event.getMessages();
                for (Message message : messages) {

                    try {

                        EmailRequest emailRequest = new EmailRequest();
                        emailRequest.setSubject(message.getSubject());
                        emailRequest.setSender("200107114@stu.sdu.edu.kz");
                        emailRequest.setMailBody("{\"box_name\":\"1\", \"fullness\":\"121\"}");

                       // producerService.produceSensorReport(emailRequest);
                        produceMessageService.produceMessage("Hi");
                        System.out.println("New email received: " + message.getSubject());
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // Start the IDLE Loop
        while (!Thread.interrupted()) {
            try {
                System.out.println("Starting IDLE");
                inbox.idle();
            } catch (MessagingException e) {
                System.out.println("Messaging exception during IDLE");
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        // Interrupt and shutdown the keep-alive thread
        if (keepAliveThread.isAlive()) {
            keepAliveThread.interrupt();
        }
    }
}