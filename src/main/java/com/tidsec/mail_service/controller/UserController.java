package com.tidsec.mail_service.controller;


import com.tidsec.mail_service.entities.Role;
import com.tidsec.mail_service.entities.User;
import com.tidsec.mail_service.model.UserDTO;
import com.tidsec.mail_service.service.IRoleService;
import com.tidsec.mail_service.service.IUserService;
import lombok.RequiredArgsConstructor;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    private final IRoleService roleService;

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        List<UserDTO> userList = userService.getAll()
                .stream()
                .map(user -> UserDTO.builder()
                        .id(user.getId())
                        .dateCreate(user.getDateCreate())
                        .identification(user.getIdentification())
                        .name(user.getName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .password(user.getPassword())
                        //.role(user.getRole())
                        .status(user.getStatus())
                        .build())
                .toList();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserDTO userDTO = UserDTO.builder()
                    .id(user.getId())
                    .dateCreate(user.getDateCreate())
                    .identification(user.getIdentification())
                    .name(user.getName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    //.role(user.getRole())
                    .status(user.getStatus())
                    .build();
            return ResponseEntity.ok(userDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@RequestBody UserDTO userDTO) {
        if (userDTO.getName() == null || userDTO.getName().isBlank()) {
            return ResponseEntity.badRequest().body("El nombre del usuario es obligatorio");
        }

        List<Role> roles = userDTO.getRoles()
                .stream()
                .map(roleService::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        User user = User.builder()
                .id(userDTO.getId())
                .dateCreate(userDTO.getDateCreate())
                .identification(userDTO.getIdentification())
                .name(userDTO.getName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .roles(roles)
                .status(userDTO.getStatus())
                .build();

        User obj = userService.save(user);

        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("{id}").buildAndExpand(obj.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setIdentification(userDTO.getIdentification());
            user.setName(userDTO.getName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword());

            List<Role> updatedRoles = userDTO.getRoles()
                    .stream()
                    .map(roleService::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
            user.setRoles(updatedRoles);
            user.setStatus(userDTO.getStatus());

            userService.update(id, user);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Usuario actualizado exitosamente");
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        boolean result = userService.delete(id);
        Map<String, String> response = new HashMap<>();
        if (result) {
            response.put("message", "Usuario eliminado correctamente");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Error al intentar eliminar el usuario");
            return ResponseEntity.badRequest().body(response);
        }
    }
}
