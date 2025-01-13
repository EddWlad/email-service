package com.tidsec.mail_service.controller;

import com.tidsec.mail_service.entities.Project;
import com.tidsec.mail_service.model.ProjectDTO;
import com.tidsec.mail_service.service.IProjectService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/project")
@RequiredArgsConstructor
public class ProjectController {

    private final IProjectService projectService;
    private final ModelMapper modelMapper;

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        List<ProjectDTO> projectList = projectService.getAll()
                .stream()
                .map((Project obj) -> convertToDto(Optional.ofNullable(obj)))
                .toList();
        return ResponseEntity.ok(projectList);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Project> projectOptional = projectService.findById(id);
        if (projectOptional.isPresent()) {

            return ResponseEntity.ok(convertToDto(projectOptional));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveProject(@RequestBody ProjectDTO projectDTO) {
        if (projectDTO.getName() == null || projectDTO.getName().isBlank() ||
                projectDTO.getCompany() == null || projectDTO.getCompany().isBlank()) {
            return ResponseEntity.badRequest().body("El nombre del proyecto y la compañía son necesarios");
        }
        Project obj = projectService.save(convertToEntity(projectDTO));

        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("{id}").buildAndExpand(obj.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProject(@PathVariable Long id, @RequestBody ProjectDTO projectDTO) {
        Optional<Project> projectOptional = projectService.findById(id);
        if (projectOptional.isPresent()) {
            Project obj = projectService.update(id, convertToEntity(projectDTO));

            Map<String, String> response = new HashMap<>();
            response.put("message", "Proyecto actualizado exitosamente");

            return ResponseEntity.ok(convertToDto(Optional.ofNullable(obj)));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id) {
        boolean result = projectService.delete(id);
        Map<String, String> response = new HashMap<>();
        if (result) {
            response.put("message", "Proyecto eliminado correctamente");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Error al intentar eliminar el proyecto");
            return ResponseEntity.badRequest().body(response);
        }
    }

    private ProjectDTO convertToDto(Optional<Project> obj){
        return modelMapper.map(obj, ProjectDTO.class);
    }

    private Project convertToEntity(ProjectDTO dto){
        return modelMapper.map(dto, Project.class );
    }
}
