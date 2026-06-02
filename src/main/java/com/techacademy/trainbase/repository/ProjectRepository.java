package com.techacademy.trainbase.repository;

import com.techacademy.trainbase.entity.Project;
import com.techacademy.trainbase.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByOwnerId(User owner);
    List<Project> findByNameContainingIgnoreCase(String name);
}
