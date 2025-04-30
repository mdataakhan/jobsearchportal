package com.jobsearchportal.controller;


import com.jobsearchportal.DTO.request.CompanyRegisterRequest;
import com.jobsearchportal.DTO.request.CompanyUpdateRequest;
import com.jobsearchportal.DTO.response.CompanyProfileResponse;
import com.jobsearchportal.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/company")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping("/register")
    public ResponseEntity<String> registerNewCompany(@RequestBody CompanyRegisterRequest request){
        companyService.registerCompany(request);
        return ResponseEntity.ok("Company registered successfully");
    }
    @GetMapping("/profile")
    public ResponseEntity<CompanyProfileResponse> getProfileOfCompany(){
        CompanyProfileResponse profile = companyService.getCompanyProfile();
        return ResponseEntity.ok(profile);
    }
    @PutMapping("/update")
    public ResponseEntity<CompanyProfileResponse> updateCompany(@RequestBody CompanyUpdateRequest request){
        CompanyProfileResponse updatedProfileResponse = companyService.UpdateCompanyProfile(request);
        return ResponseEntity.ok(updatedProfileResponse);
    }
}
