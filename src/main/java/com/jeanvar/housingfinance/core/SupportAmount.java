package com.jeanvar.housingfinance.core;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.Month;
import java.time.Year;

@Getter
@Setter
@ToString
@Entity
@Table(name = "SUPPORT_AMOUNT")
public class SupportAmount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "YEAR")
    private Year year;

    @Column(name = "MONTH", columnDefinition = "SMALLINT")
    @Enumerated
    private Month month;

    @ManyToOne
    @JoinColumn(name = "INSTITUTE_ID")
    private Institute institute;

    @Column(name = "AMOUNT")
    private Integer amount;
}
