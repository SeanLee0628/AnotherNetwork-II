package com.Seanspace.Shop.Service;

import com.Seanspace.Shop.Entity.Applicant;
import com.Seanspace.Shop.Repository.ApplicantRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ApplicantService {

    private final ApplicantRepository applicantRepository;

    // Constructor-based dependency injection
    public ApplicantService(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }

    // Applicant ID를 사용하여 Applicant를 조회하는 메서드
    public String getApplicantNameById(Long id) {
        Optional<Applicant> applicant = applicantRepository.findById(id);

        // If Applicant exists, return the name; otherwise, throw an exception
        return applicant
                .map(Applicant::getName)  // Extract the name if the applicant is present
                .orElseThrow(() -> new RuntimeException("Applicant not found with id: " + id));
    }


    // Applicant 정보를 추가하는 메서드 (기존 코드)
    public void addApplicant(String name, String role, String stacks, String intro, Integer feedbacks) {
        Applicant applicant = new Applicant();
        applicant.setName(name);
        applicant.setRole(role);
        applicant.setStacks(stacks);
        applicant.setIntro(intro);
        applicant.setFeedbacks(feedbacks);

        applicantRepository.save(applicant);
    }
}
