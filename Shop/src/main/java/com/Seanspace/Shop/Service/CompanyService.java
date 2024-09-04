package com.Seanspace.Shop.Service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.Seanspace.Shop.Repository.CompanyRepository;
import com.Seanspace.Shop.Entity.Company;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    // Constructor-based dependency injection
    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    // Method to add a Company
    public void addCompany(String company, String role, String job, String conditions, String prefered, String phone, List<String> applications) {
        // Create a new Company object
        Company newCompany = new Company();
        newCompany.setCompany(company);
        newCompany.setRole(role);
        newCompany.setJob(job);
        newCompany.setConditions(conditions);
        newCompany.setPrefered(prefered);
        newCompany.setPhone(phone);
        newCompany.setApplications(applications);

        // Debugging: Print the applications list before saving
        System.out.println("Before saving, applications: " + newCompany.getApplications());

        // Save the new company using the repository
        companyRepository.save(newCompany);

        // Debugging: Fetch the saved entity and print applications
        Company savedCompany = companyRepository.findById(newCompany.getId()).orElse(null);
        if (savedCompany != null) {
            System.out.println("After saving, applications: " + savedCompany.getApplications());
        }
    }


    public List<Company> getAll(){
        return companyRepository.findAll();
    }


    public String getCompanyNameByApplicantId(Long applicantId) {
        Optional<Company> company = companyRepository.findById(applicantId);

        // Retrieve and return the company name if present, otherwise throw an exception
        return company
                .map(Company::getCompany)  // Extracts the name of the company if it is present
                .orElseThrow(() -> new RuntimeException("Company not found for applicant id: " + applicantId));
    }
    public String getCompanyJobByApplicantId(Long applicantId) {
        Optional<Company> company = companyRepository.findById(applicantId);

        // Retrieve and return the company name if present, otherwise throw an exception
        return company
                .map(Company::getJob)  // Extracts the name of the company if it is present
                .orElseThrow(() -> new RuntimeException("Company not found for applicant id: " + applicantId));
    }

    public String getCompanyRoleByApplicantId(Long applicantId) {
        Optional<Company> company = companyRepository.findById(applicantId);

        // Retrieve and return the company name if present, otherwise throw an exception
        return company
                .map(Company::getRole)  // Extracts the name of the company if it is present
                .orElseThrow(() -> new RuntimeException("Company not found for applicant id: " + applicantId));
    }
    public String getCompanyConditionsByApplicantId(Long applicantId) {
        Optional<Company> company = companyRepository.findById(applicantId);

        // Retrieve and return the company name if present, otherwise throw an exception
        return company
                .map(Company::getConditions)  // Extracts the name of the company if it is present
                .orElseThrow(() -> new RuntimeException("Company not found for applicant id: " + applicantId));
    }
    public String getCompanyPreferedByApplicantId(Long applicantId) {
        Optional<Company> company = companyRepository.findById(applicantId);

        // Retrieve and return the company name if present, otherwise throw an exception
        return company
                .map(Company::getPrefered)  // Extracts the name of the company if it is present
                .orElseThrow(() -> new RuntimeException("Company not found for applicant id: " + applicantId));
    }
    public String getCompanyPhoneByApplicantId(Long applicantId) {
        Optional<Company> company = companyRepository.findById(applicantId);

        // Retrieve and return the company name if present, otherwise throw an exception
        return company
                .map(Company::getPhone)  // Extracts the name of the company if it is present
                .orElseThrow(() -> new RuntimeException("Company not found for applicant id: " + applicantId));
    }
    public List<String> getCompanyApplicationsByApplicantId(Long applicantId) {
        Optional<Company> company = companyRepository.findById(applicantId);

        // Retrieve and return the company name if present, otherwise throw an exception
        return company
                .map(Company::getApplications)  // Extracts the name of the company if it is present
                .orElseThrow(() -> new RuntimeException("Company not found for applicant id: " + applicantId));
    }



    // Other methods can be added here
}
