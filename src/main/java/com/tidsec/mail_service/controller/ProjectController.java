package com.tidsec.mail_service.controller;

import com.tidsec.mail_service.entities.Project;
import com.tidsec.mail_service.model.ProjectDTO;
import com.tidsec.mail_service.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/project")
public class ProjectController {
    @Autowired
    private IProjectService projectService;

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        List<ProjectDTO> projectList = projectService.getAll()
                .stream()
                .map(project -> ProjectDTO.builder()
                        .id(project.getId())
                        .name(project.getName())
                        .company(project.getCompany())
                        .description(project.getDescription())
                        .status(project.getStatus())
                        .build())
                .toList();
        return ResponseEntity.ok(projectList);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Project> projectOptional = projectService.findById(id);
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            ProjectDTO projectDTO = ProjectDTO.builder()
                    .id(project.getId())
                    .name(project.getName())
                    .company(project.getCompany())
                    .description(project.getDescription())
                    .status(project.getStatus())
                    .build();
            return ResponseEntity.ok(projectDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveProject(@RequestBody ProjectDTO projectDTO) throws URISyntaxException {
        if (projectDTO.getName() == null || projectDTO.getName().isBlank() ||
                projectDTO.getCompany() == null || projectDTO.getCompany().isBlank()) {
            return ResponseEntity.badRequest().body("El nombre del proyecto y la compañía son necesarios");
        }
        projectService.saveProject(Project.builder()
                .id(projectDTO.getId())
                .name(projectDTO.getName())
                .company(projectDTO.getCompany())
                .description(projectDTO.getDescription())
                .status(projectDTO.getStatus())
                .build());
        return ResponseEntity.created(new URI("/api/project/save")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProject(@PathVariable Long id, @RequestBody ProjectDTO projectDTO) {
        Optional<Project> projectOptional = projectService.findById(id);
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            project.setName(projectDTO.getName());
            project.setCompany(projectDTO.getCompany());
            project.setDescription(projectDTO.getDescription());
            project.setStatus(projectDTO.getStatus());

            projectService.updateProject(id, project);
            return ResponseEntity.ok("Proyecto actualizado exitosamente");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id) {
        boolean result = projectService.deleteProject(id);
        if (result) {
            return ResponseEntity.ok("Proyecto eliminado correctamente");
        } else {
            return ResponseEntity.badRequest().body("Error al intentar eliminar el proyecto");
        }
    }
}
