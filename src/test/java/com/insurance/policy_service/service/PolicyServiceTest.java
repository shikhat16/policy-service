package com.insurance.policy_service.service;

import com.insurance.policy_service.dto.PolicyDTO;
import com.insurance.policy_service.model.Policy;
import com.insurance.policy_service.repository.PolicyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PolicyServiceTest {

    @Mock
    private PolicyRepository policyRepository;

    @InjectMocks
    private PolicyService policyService;

    private Policy samplePolicy;
    private PolicyDTO sampleDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        samplePolicy = new Policy();
        samplePolicy.setId(1L);
        samplePolicy.setPolicyNumber("POL12345");
        samplePolicy.setPolicyHolderName("John Doe");
        samplePolicy.setPolicyType("Health");
        samplePolicy.setPremiumAmount(5000.0);

        sampleDTO = new PolicyDTO(1L, "POL12345", "John Doe", "Health", 5000.0);
    }

    @Test
    void testSavePolicy() {
        when(policyRepository.save(any(Policy.class))).thenReturn(samplePolicy);

        PolicyDTO result = policyService.savePolicy(sampleDTO);

        assertNotNull(result);
        assertEquals("POL12345", result.getPolicyNumber());
        verify(policyRepository, times(1)).save(any(Policy.class));
    }

    @Test
    void testGetPolicyById_Found() {
        when(policyRepository.findById(1L)).thenReturn(Optional.of(samplePolicy));

        PolicyDTO result = policyService.getPolicyById(1L);

        assertNotNull(result);
        assertEquals("John Doe", result.getPolicyHolderName());
        verify(policyRepository, times(1)).findById(1L);
    }

    @Test
    void testGetPolicyById_NotFound() {
        when(policyRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            policyService.getPolicyById(99L);
        });

        assertEquals("Policy not found with id: 99", exception.getMessage());
    }

    @Test
    void testGetAllPolicies() {
        when(policyRepository.findAll()).thenReturn(Arrays.asList(samplePolicy));

        List<PolicyDTO> result = policyService.getAllPolicies();

        assertEquals(1, result.size());
        assertEquals("POL12345", result.get(0).getPolicyNumber());
        verify(policyRepository, times(1)).findAll();
    }

    @Test
    void testUpdatePolicy() {
        when(policyRepository.findById(1L)).thenReturn(Optional.of(samplePolicy));
        when(policyRepository.save(any(Policy.class))).thenReturn(samplePolicy);

        PolicyDTO updatedDTO = new PolicyDTO(1L, "POL99999", "Jane Doe", "Life", 6000.0);
        PolicyDTO result = policyService.updatePolicy(1L, updatedDTO);

        assertEquals("POL99999", result.getPolicyNumber());
        assertEquals("Jane Doe", result.getPolicyHolderName());
        verify(policyRepository, times(1)).save(any(Policy.class));
    }

    @Test
    void testDeletePolicy() {
        when(policyRepository.existsById(1L)).thenReturn(true);

        policyService.deletePolicy(1L);

        verify(policyRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeletePolicy_NotFound() {
        when(policyRepository.existsById(99L)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            policyService.deletePolicy(99L);
        });

        assertEquals("Policy not found with id: 99", exception.getMessage());
    }
}