package com.tidsec.mail_service.service;

import com.tidsec.mail_service.entities.Attachments;

import java.util.List;
import java.util.Optional;

public interface IAttachmentsService extends IGenericService<Attachments, Long>{
    /*List<Attachments> getAll();
    Optional<Attachments> findById(Long id);
    Attachments saveAttachments(Attachments attachments);
    Attachments updateAttachments(Long id, Attachments attachments);
    public boolean deleteAttachments(Long id);
    Long countAttachments();*/

    List<Attachments> findAttachmentsByMailAndStatus(Long mailId, int excludedStatus);
}
