package kz.sparklab.mailnode;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {
    private String sender;
    private String subject;
    private String mailBody;
}
