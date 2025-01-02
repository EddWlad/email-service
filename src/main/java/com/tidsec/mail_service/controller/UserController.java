package com.tidsec.mail_service.controller;


import com.tidsec.mail_service.entities.Role;
import com.tidsec.mail_service.entities.User;
import com.tidsec.mail_service.model.UserDTO;
import com.tidsec.mail_service.service.IRoleService;
import com.tidsec.mail_service.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

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
    public ResponseEntity<?> saveUser(@RequestBody UserDTO userDTO) throws URISyntaxException {
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

        userService.saveUser(user);

        return ResponseEntity.created(new URI("/api/user/save")).build();
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

            userService.updateUser(id, user);
            return ResponseEntity.ok("Usuario actualizado exitosamente");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        boolean result = userService.deleteUser(id);
        if (result) {
            return ResponseEntity.ok("Usuario eliminado exitosamente");
        } else {
            return ResponseEntity.badRequest().body("Error al intentar eliminar el usuario");
        }
    }
}
