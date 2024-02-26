package kz.sparklab.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import org.telegram.telegrambots.meta.api.objects.Update;

@Entity
@Getter
@Setter
@EqualsAndHashCode()
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RawData {@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private Update event;
}
