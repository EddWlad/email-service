package com.tidsec.mail_service.service.impl;

import com.tidsec.mail_service.entities.Attachments;
import com.tidsec.mail_service.repositories.IAttachmentsRepository;
import com.tidsec.mail_service.service.IAttachmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttachmentsServiceImpl implements IAttachmentsService {
    @Autowired
    private IAttachmentsRepository attachmentsRepository;

    @Override
    public List<Attachments> getAll() {
        return attachmentsRepository.findByStatusNot(0) ;
    }

    @Override
    public Optional<Attachments> findById(Long id) {
        return attachmentsRepository.findById(id);
    }

    @Override
    public Attachments saveAttachments(Attachments attachments) {
        return attachmentsRepository.save(attachments);
    }

    @Override
    public Attachments updateAttachments(Long id, Attachments attachments) {
        Attachments attachmentsDb = attachmentsRepository.findById(id).orElse(null);
        if(attachments != null) {
            attachmentsDb.setMail(attachments.getMail());
            attachmentsDb.setRouteAttachment(attachments.getRouteAttachment());
            attachmentsDb.setStatus(attachments.getStatus());
            return attachmentsRepository.save(attachmentsDb);
        }else{
            return null;
        }

    }

    @Override
    public boolean deleteAttachments(Long id) {
        Attachments attachmentsDb = attachmentsRepository.findById(id).orElse(null);
        if(attachmentsDb != null){
            attachmentsDb.setStatus(0);
            attachmentsRepository.save(attachmentsDb);
            return true;
        }else {
            return false;
        }

    }

    @Override
    public Long countAttachments() {
        return attachmentsRepository.count();
    }

    @Override
    public List<Attachments> findAttachmentsByMailAndStatus(Long mailId, int excludedStatus) {
        return attachmentsRepository.findByMailIdAndStatusNot(mailId, excludedStatus);
    }
}
