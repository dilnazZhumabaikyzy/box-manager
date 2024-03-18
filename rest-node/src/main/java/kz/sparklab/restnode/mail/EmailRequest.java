package kz.sparklab.restnode.mail;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmailRequest {
    @JsonProperty("box_name")
    private String boxName;

    @JsonProperty("fullness")
    private String fullness;

    @Override
    public String toString() {
        return "EmailRequest{" +
                "boxName='" + boxName + '\'' +
                ", fullness='" + fullness + '\'' +
                '}';
    }
}