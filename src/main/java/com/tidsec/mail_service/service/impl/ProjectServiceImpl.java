package com.tidsec.mail_service.service.impl;

import com.tidsec.mail_service.entities.Project;
import com.tidsec.mail_service.exception.ModelNotFoundException;
import com.tidsec.mail_service.repositories.IGenericRepository;
import com.tidsec.mail_service.repositories.IProjectRepository;
import com.tidsec.mail_service.service.IProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl extends GenericServiceImpl<Project, Long> implements IProjectService {


    private final IProjectRepository projectRepository;

    @Override
    protected IGenericRepository<Project, Long> getRepo() {
        return projectRepository;
    }

    /*@Override
    public List<Project> getAll() {
        return projectRepository.findByStatusNot(0);
    }

    @Override
    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id)
                .filter(project -> project.getStatus() != 0)
                .or(() -> {
                    throw new ModelNotFoundException("ID NOT FOUND: " + id);
                });
    }

    @Override
    public Project save(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project update(Long id, Project project) {
        Project projectDb = projectRepository.findById(id).orElse(null);
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
    public boolean delete(Long id) {
        Project projectDb = projectRepository.findById(id)
                .filter(project -> project.getStatus() != 0)
                .orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND OR INACTIVE: " + id));
        projectDb.setStatus(0);
        projectRepository.save(projectDb);
        return true;
    }

    @Override
    public Long count() {
        return projectRepository.count();
    }*/
}
