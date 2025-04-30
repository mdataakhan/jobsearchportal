package com.jobsearchportal.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class CompanyProfileResponse {
    private Long id;
    private String companyName;
    private String address;
    private String phone;
    private String email;
    private String website;
}
