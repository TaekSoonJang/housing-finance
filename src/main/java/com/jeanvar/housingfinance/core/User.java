package com.jeanvar.housingfinance.core;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NaturalId
    @Column(name = "USER_ID", unique = true)
    private String userId;

    @Column(name = "PASSWORD", length = 60) // bcrypt compatible
    private String password;
}
