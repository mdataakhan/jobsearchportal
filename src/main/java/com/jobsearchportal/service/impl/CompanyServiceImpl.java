package com.jobsearchportal.service.impl;

import com.jobsearchportal.DTO.request.CompanyRegisterRequest;
import com.jobsearchportal.DTO.request.CompanyUpdateRequest;
import com.jobsearchportal.DTO.response.CompanyProfileResponse;
import com.jobsearchportal.entity.Company;
import com.jobsearchportal.exception.ResourceNotFoundException;
import com.jobsearchportal.repository.CompanyRepository;
import com.jobsearchportal.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void registerCompany(CompanyRegisterRequest request) {
        Company company = new Company();
        company.setCompanyName(request.getCompanyName());
        company.setEmail(request.getEmail());
        company.setPassword(passwordEncoder.encode(request.getPassword()));
        company.setCreatedAt(LocalDateTime.now());
        companyRepository.save(company);
    }

    @Override
    public CompanyProfileResponse getCompanyProfile() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Company company = companyRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("Company Not Found"));
        return mapToCompanyProfile(company);
    }

    @Override
    public CompanyProfileResponse UpdateCompanyProfile(CompanyUpdateRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Company company = companyRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));
        company.setCompanyName(request.getCompanyName());
        company.setAddress(request.getAddress());
        company.setPhone(request.getPhone());
        company.setWebsite(request.getWebsite());
        companyRepository.save(company);
        return mapToCompanyProfile(company);
    }
    private CompanyProfileResponse mapToCompanyProfile(Company company) {
        return  new CompanyProfileResponse(
                company.getId(),
                company.getCompanyName(),
                company.getAddress(),
                company.getPhone(),
                company.getEmail(),
                company.getWebsite()
        );
    }
}
