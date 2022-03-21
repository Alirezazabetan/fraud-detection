package com.surepay.frauddetection.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * A Fraud.
 */
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Fraud extends AbstractAuditingEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    private Double reference;
    private String accountNumber;
    private BigDecimal startBalance;
    private BigDecimal mutation;
    private String description;
    private BigDecimal endBalance;
}
