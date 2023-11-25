package com.uzum.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "commissions")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "currency_from")
    private String from;

    @Column(name = "currency_to")
    private String to;
    private Integer commission;

    @Column(name = "conversion_rate")
    private Double conversionRate;
}
