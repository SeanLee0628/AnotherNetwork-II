package com.Seanspace.Shop.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@Setter
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String company;
    private String role;
    private String job;
    private String conditions;
    private String prefered;
    private String phone;

    // Correctly mapped applications as an ElementCollection
    @ElementCollection
    @CollectionTable(name = "company_applications", joinColumns = @JoinColumn(name = "company_id"))
    @Column(name = "application")
    private List<String> applications = new ArrayList<>();
}