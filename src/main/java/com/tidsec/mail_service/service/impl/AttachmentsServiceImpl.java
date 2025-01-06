package com.tidsec.mail_service.service.impl;

import com.tidsec.mail_service.entities.Attachments;
import com.tidsec.mail_service.entities.Project;
import com.tidsec.mail_service.exception.ModelNotFoundException;
import com.tidsec.mail_service.repositories.IAttachmentsRepository;
import com.tidsec.mail_service.repositories.IGenericRepository;
import com.tidsec.mail_service.service.IAttachmentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttachmentsServiceImpl extends GenericServiceImpl<Attachments, Long> implements IAttachmentsService {

    private final IAttachmentsRepository attachmentsRepository;

    @Override
    protected IGenericRepository<Attachments, Long> getRepo() {
        return attachmentsRepository;
    }

    /*@Override
    public List<Attachments> getAll() {
        return attachmentsRepository.findByStatusNot(0) ;
    }

    @Override
    public Optional<Attachments> findById(Long id) {
        return attachmentsRepository.findById(id)
                .filter(attachments -> attachments.getStatus() != 0)
                .or(() -> {
                    throw new ModelNotFoundException("ID NOT FOUND: " + id);
                });
    }

    @Override
    public Attachments save(Attachments attachments) {
        return attachmentsRepository.save(attachments);
    }

    @Override
    public Attachments update(Long id, Attachments attachments) {
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
    public boolean delete(Long id) {
        Attachments attachmentstDb = attachmentsRepository.findById(id)
                .filter(attachments -> attachments.getStatus() != 0)
                .orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND OR INACTIVE: " + id));
        attachmentstDb.setStatus(0);
        attachmentsRepository.save(attachmentstDb);
        return true;

    }

    @Override
    public Long count() {
        return attachmentsRepository.count();
    }*/

    @Override
    public List<Attachments> findAttachmentsByMailAndStatus(Long mailId, int excludedStatus) {
        return attachmentsRepository.findByMailIdAndStatusNot(mailId, excludedStatus);
    }


}
