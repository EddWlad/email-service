package com.tidsec.mail_service.service.impl;

import com.tidsec.mail_service.entities.Project;
import com.tidsec.mail_service.entities.Role;
import com.tidsec.mail_service.exception.ModelNotFoundException;
import com.tidsec.mail_service.repositories.IGenericRepository;
import com.tidsec.mail_service.repositories.IRoleRepository;
import com.tidsec.mail_service.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends GenericServiceImpl<Role, Long> implements IRoleService {

    private final IRoleRepository roleRepository;

    @Override
    protected IGenericRepository<Role, Long> getRepo() {
        return null;
    }

    /*@Override
    public List<Role> getAll() {
        return roleRepository.findByStatusNot(0);
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id)
                .filter(role -> role.getStatus() != 0)
                .or(() -> {
                    throw new ModelNotFoundException("ID NOT FOUND: " + id);
                });
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role update(Long id, Role role) {
        Role roleDb = roleRepository.findById(id).orElse(null);
        if(role != null)
        {
            roleDb.setName(role.getName());
            roleDb.setDescription((role.getDescription()));
            roleDb.setStatus(role.getStatus());
            return roleRepository.save(roleDb);
        }
        else {
            return null;
        }

    }

    @Override
    public boolean delete(Long id) {
        Role roleDb = roleRepository.findById(id)
                .filter(role -> role.getStatus() != 0)
                .orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND OR INACTIVE: " + id));
        roleDb.setStatus(0);
        roleRepository.save(roleDb);
        return true;
    }

    @Override
    public Long count() {
        return roleRepository.count();
    }*/
}
