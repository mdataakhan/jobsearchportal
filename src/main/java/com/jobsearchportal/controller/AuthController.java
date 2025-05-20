package com.jobsearchportal.controller;

import com.jobsearchportal.dto.LoginDTO;
import com.jobsearchportal.entity.Candidate;
import com.jobsearchportal.entity.Company;
import com.jobsearchportal.service.CandidateService;
import com.jobsearchportal.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        String role = loginDTO.getRole();

        if ("candidate".equalsIgnoreCase(role)) {
            Optional<Candidate> candidateOpt = candidateService.findCandidateByEmail(loginDTO.getEmail());
            if (candidateOpt.isPresent()) {
                Candidate candidate = candidateOpt.get();
                if (passwordEncoder.matches(loginDTO.getPassword(), candidate.getPassword())) {
                    return ResponseEntity.ok(candidate);
                }
            }
        } else if ("company".equalsIgnoreCase(role)) {
            Optional<Company> companyOpt = companyService.findCompanyByEmail(loginDTO.getEmail());
            if (companyOpt.isPresent()) {
                Company company = companyOpt.get();
                if (passwordEncoder.matches(loginDTO.getPassword(), company.getPassword())) {
                    return ResponseEntity.ok(company);
                }
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
}
