package com.alireza.java_code_challenge.entity;


import com.alireza.java_code_challenge.entity.enumeration.TokenType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Token implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String token;

    @Enumerated(EnumType.STRING)
    TokenType type;

    boolean expired;
    boolean revoked;

    @ManyToOne
    User user;
}
