package com.tidsec.mail_service.repositories;

import com.tidsec.mail_service.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProjectRepository extends IGenericRepository<Project, Long> {
}
