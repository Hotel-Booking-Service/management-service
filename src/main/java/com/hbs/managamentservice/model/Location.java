package com.hbs.managamentservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "location")
    private Hotel hotel;

    @Column(length = 100, nullable = false)
    private String country;

    @Column(length = 150, nullable = false)
    private String city;

    @Column(nullable = false)
    private String street;

    @Column(length = 25, nullable = false)
    private String building;

    @Column(length = 25, nullable = false)
    private String zipCode;
}
