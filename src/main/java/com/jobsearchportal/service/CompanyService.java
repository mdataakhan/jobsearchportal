package com.jobsearchportal.service;

import com.jobsearchportal.DTO.request.CompanyRegisterRequest;
import com.jobsearchportal.DTO.request.CompanyUpdateRequest;
import com.jobsearchportal.DTO.response.CompanyProfileResponse;

public interface CompanyService {
   void registerCompany(CompanyRegisterRequest request);
   CompanyProfileResponse getCompanyProfile();
   CompanyProfileResponse UpdateCompanyProfile(CompanyUpdateRequest request);
}
