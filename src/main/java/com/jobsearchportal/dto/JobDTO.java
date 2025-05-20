package com.jobsearchportal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobDTO {

    private String id;

    @NotBlank(message = "Position is required")
    @Size(max = 100, message = "Position must be less than 100 characters")
    private String position;

    @NotBlank(message = "Company ID is required")
    private String companyId;

    @NotBlank(message = "Location is required")
    @Size(max = 255, message = "Location must be less than 255 characters")
    private String location;

    @NotBlank(message = "Experience is required")
    @Size(max = 50, message = "Experience must be less than 50 characters")
    private String experience;

    @NotBlank(message = "Description is required")
    @Size(max = 2000, message = "Description must be less than 2000 characters")
    private String description;

    @NotEmpty(message = "Skills cannot be empty")
    private List<String> skills = new ArrayList<>();

    @NotBlank(message = "Job type is required")
    @Size(max = 50, message = "Job type must be less than 50 characters")
    private String jobType;
}
