package com.Seanspace.Shop.Controller;

import com.Seanspace.Shop.Entity.Applicant;
import com.Seanspace.Shop.Entity.Company;
import com.Seanspace.Shop.Repository.ApplicantRepository;
import com.Seanspace.Shop.Service.ApplicantService;
import com.Seanspace.Shop.Service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ApplicantController {

    private final ApplicantService applicantService;
    private final ApplicantRepository applicantRepository;
    private final CompanyService companyService;

    @GetMapping("/inputApp")
    String inputApp(){
        return "inputApp.html";
    }

    @PostMapping("/addApplicant")
    String addApplicant(@RequestParam String name, @RequestParam String role, @RequestParam String stacks, @RequestParam String intro, @RequestParam Integer feedbacks, Model model){
        applicantService.addApplicant(name, role, stacks, intro, feedbacks);
        List<Company> result = companyService.getAll();
        model.addAttribute("result", result);
        model.addAttribute("name", name);

        return "list.html";  // Ensure 'list.html' exists in your templates folder
    }

    @PostMapping("/addCompanyToApplicant")
    public String addCompanyToApplicant(@RequestParam Long applicantId, @RequestParam String companyName, Model model) {
        // companyName이 비어 있는지 확인
        if (companyName == null || companyName.trim().isEmpty()) {
            model.addAttribute("error", "Company name cannot be empty");
            return "error"; // 오류 처리 페이지
        }

        // Applicant 정보를 조회
        Optional<Applicant> applicantOptional = applicantRepository.findById(applicantId);

        if (applicantOptional.isPresent()) {
            Applicant applicant = applicantOptional.get();

            // Applicant의 company_list에 "GOOGLE" 추가
            if (applicant.getCompanyList() == null) {
                applicant.setCompanyList(new ArrayList<>());
            }
            applicant.getCompanyList().add(companyName);

            // 변경된 Applicant 정보를 저장
            applicantRepository.save(applicant);

            model.addAttribute("companyName", companyName);
            model.addAttribute("applicant", applicant); // Applicant 객체를 모델에 추가
        } else {
            // Applicant가 없을 경우 처리 로직 (옵션)
            model.addAttribute("error", "Applicant not found");
            return "error"; // 에러 처리 페이지 템플릿 이름
        }

        // Applicant 상세 페이지로 리디렉션
        return "detail"; // Ensure this template exists in your templates folder
    }


}
