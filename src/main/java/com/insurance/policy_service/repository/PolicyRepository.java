package com.insurance.policy_service.repository;

import com.insurance.policy_service.model.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {
    // Custom queries can be added here later
}