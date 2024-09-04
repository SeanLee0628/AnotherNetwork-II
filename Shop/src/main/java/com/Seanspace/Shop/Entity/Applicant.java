package com.Seanspace.Shop.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Applicant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    String role;

    String stacks;

    String intro;

    Integer feedbacks;

    @ElementCollection
    @CollectionTable(name = "company_list", joinColumns = @JoinColumn(name = "applicant_id")) // Correct foreign key
    @Column(name = "company")
    private List<String> companyList = new ArrayList<>();

    public List<String> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<String> companyList) {
        this.companyList = companyList;
    }

    public void addCompanyToList(String company) {
        this.companyList.add(company);
    }

}
