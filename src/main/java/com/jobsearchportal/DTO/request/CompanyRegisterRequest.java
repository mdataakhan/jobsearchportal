package com.jobsearchportal.DTO.request;

import lombok.Data;

@Data
public class CompanyRegisterRequest {
    private String companyName;
    private String email;
    private String password;
}
