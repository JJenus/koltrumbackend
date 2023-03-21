package com.koltrum.koltrum.service;

import com.koltrum.koltrum.model.Plan;
import com.koltrum.koltrum.model.SubscriptionBenefit;
import com.koltrum.koltrum.repository.PlanRepository;
import com.koltrum.koltrum.repository.SubscriptionBenefitRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanService {
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private SubscriptionBenefitRepo subscriptionBenefitRepo;

    public List<Plan> getAll() {
        return planRepository.findAll();
    }

    public Plan createPlan(Plan plan) {
        return planRepository.save(plan);
    }

    public Plan getPlan(Long id) {
        return planRepository.findById(id).orElse(null);
    }

    public void deletePlan(Long id) {
        if (planRepository.findById(id).isPresent()){
            planRepository.deleteById(id);
        }
    }

    public SubscriptionBenefit saveBenefit(SubscriptionBenefit benefit) {
        return subscriptionBenefitRepo.save(benefit);
    }
}
