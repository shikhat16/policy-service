package com.insurance.policy_service.service;

import com.insurance.policy_service.dto.PolicyDTO;
import com.insurance.policy_service.model.Policy;
import com.insurance.policy_service.repository.PolicyRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PolicyService {

    private final PolicyRepository policyRepository;

    public PolicyService(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    // Get all policies (non-paginated)
    public List<PolicyDTO> getAllPolicies() {
        return policyRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get all policies (paginated + sorted)
    public Page<PolicyDTO> getAllPolicies(Pageable pageable) {
        return policyRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    // Save new policy
    public PolicyDTO savePolicy(PolicyDTO policyDTO) {
        Policy policy = convertToEntity(policyDTO);
        Policy saved = policyRepository.save(policy);
        return convertToDTO(saved);
    }

    // Get policy by ID
    public PolicyDTO getPolicyById(Long id) {
        Policy policy = policyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Policy not found with id: " + id));
        return convertToDTO(policy);
    }

    // Update existing policy
    public PolicyDTO updatePolicy(Long id, PolicyDTO policyDTO) {
        Policy existingPolicy = policyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Policy not found with id: " + id));

        existingPolicy.setPolicyNumber(policyDTO.getPolicyNumber());
        existingPolicy.setPolicyHolderName(policyDTO.getPolicyHolderName());
        existingPolicy.setPolicyType(policyDTO.getPolicyType());
        existingPolicy.setPremiumAmount(policyDTO.getPremiumAmount());

        Policy updated = policyRepository.save(existingPolicy);
        return convertToDTO(updated);
    }

    // Delete policy
    public void deletePolicy(Long id) {
        if (!policyRepository.existsById(id)) {
            throw new IllegalArgumentException("Policy not found with id: " + id);
        }
        policyRepository.deleteById(id);
    }

    // Conversion helpers
    private PolicyDTO convertToDTO(Policy policy) {
        return new PolicyDTO(
                policy.getId(),
                policy.getPolicyNumber(),
                policy.getPolicyHolderName(),
                policy.getPolicyType(),
                policy.getPremiumAmount()
        );
    }

    private Policy convertToEntity(PolicyDTO dto) {
        Policy policy = new Policy();
        policy.setPolicyNumber(dto.getPolicyNumber());
        policy.setPolicyHolderName(dto.getPolicyHolderName());
        policy.setPolicyType(dto.getPolicyType());
        policy.setPremiumAmount(dto.getPremiumAmount());
        return policy;
    }
}