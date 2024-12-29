package com.tidsec.mail_service.repositories;

import com.tidsec.mail_service.entities.Recipients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IRecipientsRepository extends JpaRepository<Recipients, Long> {
    List<Recipients> findByStatusNot(Integer status);
}
