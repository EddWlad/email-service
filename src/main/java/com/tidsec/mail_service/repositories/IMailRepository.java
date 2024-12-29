package com.tidsec.mail_service.repositories;

import com.tidsec.mail_service.entities.Mail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMailRepository extends JpaRepository<Mail, Long> {
    List<Mail> findByStatusNot(Integer status);
}
