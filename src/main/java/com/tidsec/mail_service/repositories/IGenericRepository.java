package com.tidsec.mail_service.repositories;

import com.tidsec.mail_service.entities.Attachments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@NoRepositoryBean
public interface IGenericRepository<T, ID> extends JpaRepository<T, ID> {
    List<T> findByStatusNot(Integer status);

}
