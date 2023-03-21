package com.koltrum.koltrum.service;

import com.koltrum.koltrum.model.Plan;
import com.koltrum.koltrum.model.Project;
import com.koltrum.koltrum.model.SubscriptionBenefit;
import com.koltrum.koltrum.repository.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {
    @Autowired
    ProjectRepo projectRepo;
    @Autowired
    PlanService planService;

    public List<Project> getAll() {
        return projectRepo.findAll();
    }

    public Project createProject(Project project) {
        List<Plan> plans = new ArrayList<>(project.getPlans().size());
        project.getPlans().forEach(
                plan -> {
                    List<SubscriptionBenefit> benefits = new ArrayList<>(plan.getBenefits().size());
                    plan.getBenefits().forEach(
                            benefit -> {
                                if (benefit.getId() == null){
                                    benefits.add(planService.saveBenefit(benefit));
                                }
                            }
                    );
                    plan.setBenefits(benefits);
                    plans.add(planService.createPlan(plan));
                }
        );
        project.setPlans(plans);

        return projectRepo.save(project);
    }

    public Project getProject(Long id) {
        return projectRepo.findById(id).orElse(null);
    }

    public void deleteProject(Long id) {
        if (projectRepo.findById(id).isPresent()){
            projectRepo.deleteById(id);
        }
    }
}
