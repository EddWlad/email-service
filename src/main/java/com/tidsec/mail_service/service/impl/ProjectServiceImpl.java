package com.tidsec.mail_service.service.impl;

import com.tidsec.mail_service.entities.Project;
import com.tidsec.mail_service.exception.ModelNotFoundException;
import com.tidsec.mail_service.repositories.IProjectRepository;
import com.tidsec.mail_service.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements IProjectService {

    @Autowired
    private IProjectRepository projectRepository;

    @Override
    public List<Project> getAll() {
        return projectRepository.findByStatusNot(0);
    }

    @Override
    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id);
    }

    @Override
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(Long id, Project project) {
        Project projectDb = projectRepository.findById(id).orElseThrow(()-> new ModelNotFoundException("ID NOT FOUND: " + id));
        if(project != null){
            projectDb.setName(project.getName());
            projectDb.setCompany(project.getCompany());
            projectDb.setDescription(project.getDescription());
            projectDb.setStatus(project.getStatus());
            return projectRepository.save(projectDb);
        }else {
            return null;
        }

    }

    @Override
    public boolean deleteProject(Long id) {
        Project projectDb = projectRepository.findById(id).orElseThrow(()-> new ModelNotFoundException("ID NOT FOUND: " + id));
        if(projectDb != null){
            projectDb.setStatus(0);
            projectRepository.save(projectDb);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Long countProject() {
        return projectRepository.count();
    }
}
