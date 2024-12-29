package com.tidsec.mail_service.service;

import com.tidsec.mail_service.entities.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleService {
    List<Role> getAll();
    Optional<Role> findById(Long id);
    Role saveRole(Role role);
    Role updateRole(Long id, Role role);
    public boolean deleteRole(Long id);
    Long countRole();
}
