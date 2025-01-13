package com.tidsec.mail_service.controller;

import com.tidsec.mail_service.entities.Role;
import com.tidsec.mail_service.model.RoleDTO;
import com.tidsec.mail_service.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/role")
@RequiredArgsConstructor
public class RoleController {

    private final IRoleService roleService;
    private final ModelMapper modelMapper;

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        List<RoleDTO> roleList = roleService.getAll()
                .stream()
                .map((Role obj) -> convertToDto(Optional.ofNullable(obj)))
                .toList();
        return ResponseEntity.ok(roleList);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Role> roleOptional = roleService.findById(id);
        if (roleOptional.isPresent()) {
            return ResponseEntity.ok(convertToDto(roleOptional));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveRole(@RequestBody RoleDTO roleDTO) {
        if (roleDTO.getName() == null || roleDTO.getName().isBlank()) {
            return ResponseEntity.badRequest().body("El nombre del rol es obligatorio");
        }
        Role obj = roleService.save(convertToEntity(roleDTO));

        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("{id}").buildAndExpand(obj.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRole(@PathVariable Long id, @RequestBody RoleDTO roleDTO) {
        Optional<Role> roleOptional = roleService.findById(id);
        if (roleOptional.isPresent()) {
            Role obj =  roleService.update(id, convertToEntity(roleDTO));
            Map<String, String> response = new HashMap<>();
            response.put("message", "Rol actualizado exitosamente");

            return ResponseEntity.ok(convertToDto(Optional.ofNullable(obj)));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Long id) {
        boolean result = roleService.delete(id);
        Map<String, String> response = new HashMap<>();
        if (result) {
            response.put("message", "Rol eliminado correctamente");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Error al intentar eliminar el rol");
            return ResponseEntity.badRequest().body(response);
        }
    }

    private RoleDTO convertToDto(Optional<Role> obj){
        return modelMapper.map(obj, RoleDTO.class);
    }
    private Role convertToEntity(RoleDTO dto){
        return modelMapper.map(dto, Role.class);
    }
}
