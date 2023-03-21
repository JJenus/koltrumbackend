package com.koltrum.koltrum.controller;

import com.koltrum.koltrum.model.Project;
import com.koltrum.koltrum.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/projects")
public class ProjectsController {
    @Autowired
    ProjectService projectService;

    @GetMapping
    List<Project> get(){
        return projectService.getAll();
    }

    @PostMapping
    Project create(@RequestBody Project project){
        return  projectService.createProject(project);
    }

//    @PostMapping
//    List<Project> create(@RequestBody List<Project> projects){
//        List<Project> projectList = new ArrayList<>();
//
//        projects.forEach(project ->{
//            projectList.add(projectService.createProject(project));
//        });
//
//        return projectList;
//    }

    @PutMapping
    Project update(@RequestBody Project project){
        return projectService.createProject(project);
    }

    @GetMapping("/{id}")
    Project getProject(@PathVariable("id") Long id){
        return projectService.getProject(id);
    }

    @DeleteMapping("/{id}")
    void del(@PathVariable("id") Long id){
        projectService.deleteProject(id);
    }
}
