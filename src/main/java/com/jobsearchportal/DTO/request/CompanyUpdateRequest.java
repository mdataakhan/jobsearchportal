package com.jobsearchportal.DTO.request;

import lombok.Data;

@Data
public class CompanyUpdateRequest {
    private String companyName;
    private String address;
    private String phone;
    private String website;
}
