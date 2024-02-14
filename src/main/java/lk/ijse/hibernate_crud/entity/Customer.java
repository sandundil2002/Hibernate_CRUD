package lk.ijse.hibernate_crud.entity;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @Column(name = "customer_id")
    private String id;

    @Column(name = "customer_name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "mobile")
    private String mobile;

}
