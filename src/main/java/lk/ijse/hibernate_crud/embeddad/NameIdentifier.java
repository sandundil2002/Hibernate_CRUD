package lk.ijse.hibernate_crud.embeddad;

import jakarta.persistence.Embeddable;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable

public class NameIdentifier {
    private String fistName;
    private String lastName;
}

