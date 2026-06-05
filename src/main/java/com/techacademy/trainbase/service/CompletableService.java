/*
package com.techacademy.trainbase.service;

import com.techacademy.trainbase.dto.ProjectDTO;
import com.techacademy.trainbase.entity.Project;
import com.techacademy.trainbase.exception.ResourceNotFoundException;
import com.techacademy.trainbase.mapper.ProjectMapper;
import com.techacademy.trainbase.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompletableService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final ExecutorService virtualThreadExecutor;


    // 4.5 Basic CompletableFuture
    public CompletableFuture<ProjectDTO> getProjectWithDetailsAsync(Long projectId) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("Fetching project details for ID: {}", projectId);
            // Use logger instead of System.out.println; thread names are provided by the executor
            log.info("Current Thread Service: {}", Thread.currentThread());
            Project project = projectRepository.findById(projectId)
                    .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
            return projectMapper.toDTO(project);
        }, virtualThreadExecutor);
    }
}
*/
