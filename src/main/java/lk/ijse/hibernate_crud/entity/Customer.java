package lk.ijse.hibernate_crud.entity;

import jakarta.persistence.*;
import lk.ijse.hibernate_crud.embeddad.NameIdentifier;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "customer_name")
    private NameIdentifier name;

    @Column(name = "address")
    private String address;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "addedDateTime")
    @CreationTimestamp
    private Timestamp addedDateTime;

    public Customer(String firstName, String lastName, String address, String mobile) {
        this.name = new NameIdentifier(firstName , lastName);
        this.address = address;
        this.mobile = mobile;
    }
}
