package com.jeanvar.housingfinance.core;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "INSTITUTE")
public class Institute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NaturalId
    @Column(name = "CODE", unique = true)
    private String code;

    @Column(name = "NAME")
    private String name;
}
