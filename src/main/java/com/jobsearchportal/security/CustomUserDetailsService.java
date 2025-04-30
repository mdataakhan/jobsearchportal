package com.jobsearchportal.security;

import com.jobsearchportal.entity.Company;
import com.jobsearchportal.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final CompanyRepository companyRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Company company = companyRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Company not found"));
        return new User(company.getEmail(), company.getPassword(), new ArrayList<>());
    }
}
