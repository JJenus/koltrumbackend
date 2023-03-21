package com.koltrum.koltrum.controller;

import com.koltrum.koltrum.model.Plan;
import com.koltrum.koltrum.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/plans")
public class PlanController {
    @Autowired
    PlanService planService;

    @GetMapping
    List<Plan> get(){
        return planService.getAll();
    }

    @PostMapping
    Plan create(@RequestBody Plan bankCard){
        return  planService.createPlan(bankCard);
    }

    @PutMapping
    Plan update(@RequestBody Plan bankCard){
        return planService.createPlan(bankCard);
    }

    @GetMapping("/{id}")
    Plan getPlan(@PathVariable("id") Long id){
        return planService.getPlan(id);
    }

    @DeleteMapping("/{id}")
    void del(@PathVariable("id") Long id){
        planService.deletePlan(id);
    }
}
