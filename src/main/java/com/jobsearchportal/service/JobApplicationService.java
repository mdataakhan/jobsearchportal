package com.jobsearchportal.service;

import com.jobsearchportal.entity.JobApplication;
import com.jobsearchportal.repository.CandidateRepository;
import com.jobsearchportal.repository.JobApplicationRepository;
import com.jobsearchportal.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class JobApplicationService {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private Validator validator;

    public JobApplication createJobApplication(JobApplication jobApplication) {
        // Validate entity
        Set<ConstraintViolation<JobApplication>> violations = validator.validate(jobApplication);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        // Validate candidateId and jobId
        candidateRepository.findById(jobApplication.getCandidateId())
                .orElseThrow(() -> new IllegalArgumentException("Candidate not found with id: " + jobApplication.getCandidateId()));
        jobRepository.findById(jobApplication.getJobId())
                .orElseThrow(() -> new IllegalArgumentException("Job not found with id: " + jobApplication.getJobId()));

        // Set audit fields
        jobApplication.onCreate();

        return jobApplicationRepository.save(jobApplication);
    }

    public JobApplication updateJobApplication(String id, JobApplication updatedJobApplication) {
        // Validate entity
        Set<ConstraintViolation<JobApplication>> violations = validator.validate(updatedJobApplication);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        // Find existing job application
        JobApplication jobApplication = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("JobApplication not found with id: " + id));

        // Validate candidateId and jobId
        if (!jobApplication.getCandidateId().equals(updatedJobApplication.getCandidateId())) {
            candidateRepository.findById(updatedJobApplication.getCandidateId())
                    .orElseThrow(() -> new IllegalArgumentException("Candidate not found with id: " + updatedJobApplication.getCandidateId()));
        }
        if (!jobApplication.getJobId().equals(updatedJobApplication.getJobId())) {
            jobRepository.findById(updatedJobApplication.getJobId())
                    .orElseThrow(() -> new IllegalArgumentException("Job not found with id: " + updatedJobApplication.getJobId()));
        }

        // Update fields
        jobApplication.setCandidateId(updatedJobApplication.getCandidateId());
        jobApplication.setJobId(updatedJobApplication.getJobId());
        jobApplication.setQualification(updatedJobApplication.getQualification());
        jobApplication.setResumeLink(updatedJobApplication.getResumeLink());
        jobApplication.setStatus(updatedJobApplication.getStatus());
        jobApplication.onUpdate();

        return jobApplicationRepository.save(jobApplication);
    }

    public void deleteJobApplication(String id) {
        if (!jobApplicationRepository.existsById(id)) {
            throw new IllegalArgumentException("JobApplication not found with id: " + id);
        }
        jobApplicationRepository.deleteById(id);
    }

    public JobApplication findJobApplicationById(String id) {
        return jobApplicationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("JobApplication not found with id: " + id));
    }

    public List<JobApplication> findJobApplicationsByCandidateId(String candidateId) {
        return jobApplicationRepository.findByCandidateId(candidateId);
    }

    public List<JobApplication> findJobApplicationsByJobId(String jobId) {
        return jobApplicationRepository.findByJobId(jobId);
    }

    public List<JobApplication> findJobApplicationsByStatus(String status) {
        return jobApplicationRepository.findByStatus(status);
    }
}