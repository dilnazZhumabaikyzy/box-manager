package kz.sparklab.restnode.mail;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmailRequest {
    private String sender;
    private String subject;
    private String mailBody;
}
