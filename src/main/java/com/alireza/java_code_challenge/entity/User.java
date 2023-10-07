package com.alireza.java_code_challenge.entity;


import com.alireza.java_code_challenge.entity.enumeration.Role;
import com.alireza.java_code_challenge.entity.enumeration.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "UniqueNationalCode&Email&Role",
        columnNames = {"nationalCode","email","role"})
})
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String firstname;
    String lastname;
    Long nationalCode;
    LocalDate dateOfBirth;
    Integer age;
    String email;

    @Enumerated(EnumType.STRING)
    Role role;

    @Enumerated(EnumType.STRING)
    Status status;

    @CreationTimestamp
    LocalDateTime registerDate;

    @OneToOne
    Address address;
}
