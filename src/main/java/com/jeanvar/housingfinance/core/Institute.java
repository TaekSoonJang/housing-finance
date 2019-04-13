package com.jeanvar.housingfinance.core;

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
    @GeneratedValue
    private Long id;

    @NaturalId
    @Column(name = "INSTITUTE_CODE", unique = true)
    private String instituteCode;

    @Column(name = "INSTITUTE_NAME")
    private String instituteName;
}
