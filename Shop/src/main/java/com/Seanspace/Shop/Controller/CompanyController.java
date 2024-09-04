package com.Seanspace.Shop.Controller;

import com.Seanspace.Shop.Entity.Applicant;
import com.Seanspace.Shop.Entity.Company;
import com.Seanspace.Shop.Repository.ApplicantRepository;
import com.Seanspace.Shop.Repository.CompanyRepository;
import com.Seanspace.Shop.Service.ApplicantService;
import com.Seanspace.Shop.Service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor  // Lombok annotation to generate a constructor with required arguments (final fields)
public class CompanyController {

    private final CompanyService companyService;
    private final ApplicantService applicantService;// Use final keyword and constructor-based injection
    private final CompanyRepository companyRepository;
    private final ApplicantRepository applicantRepository;

    @GetMapping("/")
    public String inputCompany() {
        return "input.html";  // Ensure 'input.html' exists in your templates folder
    }

    @PostMapping("/addCompany")
    public String addCompany(
            @RequestParam String company,
            @RequestParam String role,
            @RequestParam String job,
            @RequestParam String conditions,
            @RequestParam String prefered,
            @RequestParam String phone,
            Model model) {

        List<String> applications = new ArrayList<>();
        // Use the service layer to add the company
        companyService.addCompany(company, role, job, conditions, prefered, phone, applications);

        // Retrieve all companies from the service and add them to the model
        List<Company> result = companyService.getAll();
        model.addAttribute("result", result);
        model.addAttribute("comp", company);

        return "index.html";  // Ensure 'index.html' exists in your templates folder
    }
    @GetMapping("/addCompany")
    public String addComp(Model model){
        List<Company> result = companyService.getAll();
        model.addAttribute("result", result);
        return "index.html";
    }

    @GetMapping("/list")
    public String seeList(Model model) {
        // Fetch all Company records from the database
        List<Company> result = companyRepository.findAll();

        // Add the list of Company objects to the model
        model.addAttribute("result", result);

        // Optional: Debugging line to print the list of companies to the console
        System.out.println("HELLO" + result);

        return "list.html"; // No need to specify ".html" in the return statement
    }


    /*
    @GetMapping("/company/{id}/{company}")
    public String getCompanyById(@PathVariable Long id, @PathVariable String company, Model model) {
        Optional<Company> companyOptional = companyRepository.findById(id);

        if (companyOptional.isPresent()) {
            // 회사 정보가 존재할 경우 모델에 추가
            model.addAttribute("company", companyOptional.get());

            // `name` 파라미터가 전달되었을 경우 모델에 추가
            if (company != null) {
                model.addAttribute("name", company);
            }
        } else {
            // 회사가 존재하지 않는 경우
            return "company-not-found"; // 존재하지 않는 경우 사용할 템플릿
        }

        return "company-details.html"; // 템플릿 파일 이름은 company-details.html이어야 합니다.
    }
*/




    @GetMapping("/{id}/{company}")
    public String seeDetail(@PathVariable Long id, @PathVariable String company, Model model) {
        // id를 사용하여 Applicant 정보를 가져옴
        String applicant = applicantService.getApplicantNameById(id);

        // 모델에 Applicant와 Company 정보를 추가
        model.addAttribute("id", id);
        model.addAttribute("applicant", applicant);
        model.addAttribute("company", company); // companyName을 모델에 추가

        return "detail.html";
    }


    @GetMapping("/list/{id}")
    public String seeCompany(@PathVariable Long id, Model model) {
        String company = companyService.getCompanyNameByApplicantId(id);
        String job = companyService.getCompanyJobByApplicantId(id);
        String role = companyService.getCompanyRoleByApplicantId(id);
        String conditions = companyService.getCompanyConditionsByApplicantId(id);
        String prefered = companyService.getCompanyPreferedByApplicantId(id);
        String phone = companyService.getCompanyPhoneByApplicantId(id);
        List<String> applications = companyService.getCompanyApplicationsByApplicantId(id);
        model.addAttribute("company", company);
        model.addAttribute("job", job);
        model.addAttribute("role", role);
        model.addAttribute("conditions", conditions);
        model.addAttribute("prefered", prefered);
        return "company.html";
    }

    @PostMapping("/Applied")
    public String applyForCompany(@RequestParam Long companyId, @RequestParam String name, Model model) {
        // 회사 ID로 회사를 찾음
        Optional<Company> companyOptional = companyRepository.findById(companyId);

        if (companyOptional.isPresent()) {
            Company company = companyOptional.get();

            // 디버깅: 추가 전 지원 목록 출력
            System.out.println("Before adding, applications: " + company.getApplications());

            // 지원자 이름을 회사의 지원 목록에 추가
            company.getApplications().add(name);

            // 업데이트된 회사 엔티티 저장
            companyRepository.save(company);

            // 디버깅: 저장 후 지원 목록 출력
            System.out.println("After adding, applications: " + company.getApplications());

            // Applicant 엔티티에서 해당 이름의 지원자를 검색
            Optional<Applicant> applicantOptional = applicantRepository.findByName(name);

            if (applicantOptional.isPresent()) {
                Applicant applicant = applicantOptional.get();

                // 지원자의 companyList에 회사 이름 추가
                applicant.getCompanyList().add(company.getCompany());

                // 업데이트된 Applicant 엔티티 저장
                applicantRepository.save(applicant);

                // 디버깅: 저장 후 회사 목록 출력
                System.out.println("After adding, company list: " + applicant.getCompanyList());
            } else {
                // 지원자가 없는 경우 처리
                return "applicant-not-found.html"; // 지원자가 없을 경우 사용할 템플릿
            }

            model.addAttribute("company", company);
        } else {
            // 회사가 없는 경우 처리
            return "not-found.html"; // 회사가 없을 경우 사용할 템플릿
        }

        return "list.html"; // 업데이트된 목록을 보여줄 템플릿
    }


    @GetMapping("/app/{id}/{name}")
    public String seeMyApp(@PathVariable Long id, @PathVariable String name, Model model) {
        // `id`로 `Company`를 조회합니다.
        Optional<Company> companyOptional = companyRepository.findById(id);
        Company company = companyOptional.get();

            // Company의 application 리스트를 가져와 모델에 추가
        List<String> myApp = company.getApplications();
        model.addAttribute("id", id);
        model.addAttribute("myApp", myApp);
        model.addAttribute("name", name);
        model.addAttribute("company", company);
        System.out.println("APP" + myApp);

        return "company-details.html"; // 해당 템플릿 파일 이름으로 반환
    }
    @GetMapping("/human/{id}/{name}")
    public String seeHuman(@PathVariable Long id, @PathVariable String name, Model model) {
        Optional<Applicant> appOptional = applicantRepository.findByName(name);

        if (appOptional.isPresent()) {
            // Retrieve the applicant if found
            Applicant applicant = appOptional.get();
            String role = applicant.getRole();
            String stacks = applicant.getStacks();
            String intro = applicant.getIntro();
            model.addAttribute("role", role);
            model.addAttribute("stacks", stacks);
            model.addAttribute("intro", intro);
            return "human.html"; // Return the correct view if applicant is found
        } else {
            // Handle the case where the applicant is not found
            System.out.println("ERROR: Applicant not found with name: " + name);
            model.addAttribute("error", "Applicant not found with name: " + name);
            return "error"; // Return to a different error page
        }
    }



    @GetMapping("/company/{id}")
    public String getCompanyById(
            @PathVariable Long id,
            @RequestParam(required = false) String name,
            Model model) {

        // `id`로 회사 조회
        Optional<Company> companyOptional = companyRepository.findById(id);

        if (companyOptional.isPresent()) {
            Company company = companyOptional.get();

            // 모델에 회사 정보와 이름 추가
            model.addAttribute("company", company);
            model.addAttribute("name", name);  // name 쿼리 파라미터 추가
        } else {
            // 회사가 존재하지 않는 경우 처리
            model.addAttribute("error", "Company not found.");
            return "company-not-found";
        }

        return "company-details";  // 올바른 템플릿 이름 반환
    }

}
