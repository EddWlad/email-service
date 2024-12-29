package com.tidsec.mail_service.repositories;

import com.tidsec.mail_service.entities.Attachments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAttachmentsRepository extends JpaRepository<Attachments, Long> {
    List<Attachments> findByStatusNot(Integer status);
    List<Attachments> findByMailIdAndStatusNot(Long mailId, int status);

}
