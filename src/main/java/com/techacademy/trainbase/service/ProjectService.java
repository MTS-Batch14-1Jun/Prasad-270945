package com.techacademy.trainbase.service;

import com.techacademy.trainbase.entity.Project;
import com.techacademy.trainbase.entity.User;
import com.techacademy.trainbase.exception.ResourceNotFoundException;
import com.techacademy.trainbase.repository.ProjectRepository;
import com.techacademy.trainbase.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private UserRepository userRepository;


    @Cacheable(value = "project", key = "#id", unless = "#result == null")
    public Optional<Project> getProjectById(Long id) {
        System.out.println("-------------------");
        //log.info("Fetching project from database: {}", id);
        /*Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
        return project;*/
        return projectRepository.findById(id);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
    
   /* public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }*/
    
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }
    
    public Project updateProject(Long id, Project projectDetails) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            project.setName(projectDetails.getName());
            project.setDescription(projectDetails.getDescription());
            project.setOwnerId(projectDetails.getOwnerId());
            return projectRepository.save(project);
        }
        return null;
    }
    
    public boolean deleteProject(Long id) {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public List<Project> getProjectsByOwner(Long ownerId) {
        Optional<User> user = userRepository.findById(ownerId);
       // return user.map(projectRepository::findByOwnerId).orElse(List.of());
       return projectRepository.findByOwnerId(user.get());
    }
    
    public List<Project> searchProjectsByName(String name) {
        return projectRepository.findByNameContainingIgnoreCase(name);
    }
}
