package kz.sparklab.mailnode;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {
    @JsonProperty("box_name")
    private String boxName;

    @JsonProperty("fullness")
    private String fullness;
}