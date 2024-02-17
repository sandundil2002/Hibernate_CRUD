package lk.ijse.hibernate_crud.embeddad;

import jakarta.persistence.Embeddable;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Embeddable

public class NameIdentifier {
    private String fistName;
    private String lastName;

    @Override
    public String toString() {
        return fistName+" "+lastName;
    }
}

