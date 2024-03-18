package kz.sparklab.mailnode;

import jakarta.mail.*;


import com.sun.mail.imap.IMAPFolder;
import jakarta.mail.event.MessageCountAdapter;
import jakarta.mail.event.MessageCountEvent;
import jakarta.mail.internet.MimeMultipart;
import kz.sparklab.mailnode.service.ProduceMessageService;
import lombok.extern.log4j.Log4j;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j
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

    public void startListening() throws MessagingException {
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
                        String content = getMessageContent(message);

                        if (content == null) return;

                        String[] boxAndFullness = content.trim().split(" ");
                        String boxName = boxAndFullness[0];
                        String fullness = boxAndFullness[1];

                        log.info("Message subject: "+ boxAndFullness[0]);
                        log.info("Message content: " + boxAndFullness[1]);

                        EmailRequest emailRequest = new EmailRequest();
                        emailRequest.setBoxName(boxName);
                        emailRequest.setFullness(fullness);


                        produceMessageService.produceMessage(emailRequest);


                        log.info("New email received: " + message.getSubject());
                    } catch (MessagingException | IOException e) {
                        log.error(e);
                    }
                }
            }
        });

        // Start the IDLE Loop
        while (!Thread.interrupted()) {
            try {
                log.info("Starting IDLE");
                inbox.idle();
            } catch (MessagingException e) {
                log.error("Messaging exception during IDLE");
                log.error(e);
                throw new RuntimeException(e);
            }
        }

        if (keepAliveThread.isAlive()) {
            keepAliveThread.interrupt();
        }
    }

    private String getEmailFromAddress(Address[] addresses) {
        String senderEmail = "";
        if (addresses != null && addresses.length > 0) {
            String address = addresses[0].toString();
            if (address.contains("<") && address.contains(">")) {
                senderEmail = address.substring(address.indexOf("<") + 1, address.indexOf(">"));
            } else {
                senderEmail = address;
            }
        }
        return senderEmail;
    }

    private String getMessageContent(Message message) throws MessagingException, IOException {
        StringBuilder res = new StringBuilder();

        if (message.getContent() instanceof String content) {
            res.append(content);
        } else if (message.getContent() instanceof MimeMultipart multipart) {
            int count = multipart.getCount();
            for (int i = 0; i < count; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                if (bodyPart.isMimeType("text/plain")) {
                    res.append(bodyPart.getContent().toString());
                }
            }
        }

        log.info("raw mail: ?" + res);
        Pattern pattern = Pattern.compile("Message: (.*?)\\(");
        Matcher matcher = pattern.matcher(res);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }

}