package com.tidsec.mail_service.controller;


import com.tidsec.mail_service.entities.Role;
import com.tidsec.mail_service.entities.User;
import com.tidsec.mail_service.model.UserDTO;
import com.tidsec.mail_service.service.IRoleService;
import com.tidsec.mail_service.service.IUserService;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    private final IRoleService roleService;

    private final ModelMapper modelMapper;

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        List<UserDTO> userList = userService.getAll()
                .stream()
                .map(this::convertToDto)
                .toList();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);
        return userOptional.map(user -> ResponseEntity.ok(convertToDto(user)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@RequestBody UserDTO userDTO) {
        if (userDTO.getName() == null || userDTO.getName().isBlank()) {
            return ResponseEntity.badRequest().body("El nombre del usuario es obligatorio");
        }

        User user = convertToEntity(userDTO);
        User savedUser = userService.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            User updatedUser = convertToEntity(userDTO);
            updatedUser.setId(id);
            userService.update(id, updatedUser);

            return ResponseEntity.ok(convertToDto(updatedUser));
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

    private UserDTO convertToDto(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setRoles(
                user.getRoles()
                        .stream()
                        .map(Role::getId)
                        .collect(Collectors.toList())
        );
        return userDTO;
    }

    private User convertToEntity(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        List<Role> roles = userDTO.getRoles()
                .stream()
                .map(roleService::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        user.setRoles(roles);
        return user;
    }
}
