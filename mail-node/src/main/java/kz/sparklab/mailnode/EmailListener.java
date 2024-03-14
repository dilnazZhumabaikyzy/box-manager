package kz.sparklab.mailnode;

import jakarta.mail.*;


import com.sun.mail.imap.IMAPFolder;
import jakarta.mail.event.MessageCountAdapter;
import jakarta.mail.event.MessageCountEvent;
import jakarta.mail.internet.MimeMultipart;
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
                        String senderEmail = getEmailFromAddress(message.getFrom());
//                        if (senderEmail.equals("aestgreat@gmail.com")){
                         if (true){
                            String boxName = message.getSubject();
                            String fullness = getMessageContent(message);
                            System.out.println("Message subject: "+boxName);
                            System.out.println("Message content: " + fullness);


                            EmailRequest emailRequest = new EmailRequest();
                            emailRequest.setBoxName(boxName);
                            emailRequest.setFullness(fullness);


                            produceMessageService.produceMessage(emailRequest);
                        } else {
                            System.out.println("Email address: " + senderEmail + " is not aestgreat@gmail.com");
                        }


                        System.out.println("New email received: " + message.getSubject());
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                    catch (IOException e) {
                        throw new RuntimeException(e);
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

    private String getEmailFromAddress(Address[] addresses) {
        String senderEmail = "";
        if (addresses != null && addresses.length > 0) {
            String address = addresses[0].toString();
            // Extract email address part
            if (address.contains("<") && address.contains(">")) {
                senderEmail = address.substring(address.indexOf("<") + 1, address.indexOf(">"));
            } else {
                senderEmail = address;
            }
        }
        return senderEmail;
    }
    // Method to get the content of the message
    private String getMessageContent(Message message) throws MessagingException, IOException {
        String content = "";
        Object messageContent = message.getContent();

        if (messageContent instanceof String) {
            // Content is plain text
            content = (String) messageContent;
        } else if (messageContent instanceof MimeMultipart) {
            // Content is multipart (may contain attachments)
            MimeMultipart multipart = (MimeMultipart) messageContent;
            int count = multipart.getCount();
            for (int i = 0; i < count; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                if (bodyPart.isMimeType("text/plain")) {
                    // Found plain text part
                    content += bodyPart.getContent().toString();
                }
            }
        }

        return content;
    }

}