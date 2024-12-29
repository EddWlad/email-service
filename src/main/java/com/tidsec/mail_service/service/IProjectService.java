package com.tidsec.mail_service.service;

import com.tidsec.mail_service.entities.Project;

import java.util.List;
import java.util.Optional;

public interface IProjectService {
    List<Project> getAll();
    Optional<Project> findById(Long id);
    Project saveProject(Project project);
    Project updateProject(Long id, Project project);
    public boolean deleteProject(Long id);
    Long countProject();
}
