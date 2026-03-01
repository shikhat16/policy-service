package com.insurance.policy_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Positive;

public class PolicyDTO {

    private Long id;

    @NotBlank(message = "Policy number is required")
    @Size(min = 5, max = 20, message = "Policy number must be between 5 and 20 characters")
    private String policyNumber;

    @NotBlank(message = "Policy holder name is required")
    private String policyHolderName;

    @NotBlank(message = "Policy type is required")
    private String policyType;

    @Positive(message = "Premium amount must be greater than 0")
    private Double premiumAmount;

    // No-args constructor (required for serialization/deserialization)
    public PolicyDTO() {}

    // All-args constructor
    public PolicyDTO(Long id, String policyNumber, String policyHolderName, String policyType, Double premiumAmount) {
        this.id = id;
        this.policyNumber = policyNumber;
        this.policyHolderName = policyHolderName;
        this.policyType = policyType;
        this.premiumAmount = premiumAmount;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPolicyNumber() { return policyNumber; }
    public void setPolicyNumber(String policyNumber) { this.policyNumber = policyNumber; }

    public String getPolicyHolderName() { return policyHolderName; }
    public void setPolicyHolderName(String policyHolderName) { this.policyHolderName = policyHolderName; }

    public String getPolicyType() { return policyType; }
    public void setPolicyType(String policyType) { this.policyType = policyType; }

    public Double getPremiumAmount() { return premiumAmount; }
    public void setPremiumAmount(Double premiumAmount) { this.premiumAmount = premiumAmount; }
}