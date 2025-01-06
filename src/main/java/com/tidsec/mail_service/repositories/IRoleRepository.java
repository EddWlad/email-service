package com.tidsec.mail_service.repositories;

import com.tidsec.mail_service.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IRoleRepository extends IGenericRepository<Role, Long> {
}
