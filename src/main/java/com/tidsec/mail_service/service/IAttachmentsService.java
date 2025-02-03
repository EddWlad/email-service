package com.tidsec.mail_service.service;

import com.tidsec.mail_service.entities.Attachments;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IAttachmentsService extends IGenericService<Attachments, Long>{

    List<Attachments> findAttachmentsByMailAndStatus(Long mailId, int excludedStatus);
    List<String> saveAttachments(Long mailId, List<MultipartFile> files);
}
