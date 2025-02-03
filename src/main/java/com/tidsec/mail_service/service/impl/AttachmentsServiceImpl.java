package com.tidsec.mail_service.service.impl;

import com.tidsec.mail_service.entities.Attachments;
import com.tidsec.mail_service.entities.Mail;
import com.tidsec.mail_service.entities.Project;
import com.tidsec.mail_service.exception.ModelNotFoundException;
import com.tidsec.mail_service.repositories.IAttachmentsRepository;
import com.tidsec.mail_service.repositories.IGenericRepository;
import com.tidsec.mail_service.repositories.IMailRepository;
import com.tidsec.mail_service.service.IAttachmentsService;
import com.tidsec.mail_service.service.IFileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachmentsServiceImpl extends GenericServiceImpl<Attachments, Long> implements IAttachmentsService {

    private final IAttachmentsRepository attachmentsRepository;
    private final IFileStorageService fileStorageService;
    private final IMailRepository mailRepository;

    @Override
    public List<String> saveAttachments(Long mailId, List<MultipartFile> files) {
        Optional<Mail> mailOptional = mailRepository.findById(mailId);
        if (mailOptional.isEmpty()) {
            throw new ModelNotFoundException("Mail ID " + mailId + " no encontrado.");
        }

        List<String> fileRoutes = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                String filePath = fileStorageService.saveFile(file);

                Attachments attachment = new Attachments();
                Mail mail = new Mail();
                mail.setId(mailId);

                attachment.setMail(mail);
                attachment.setRouteAttachment(filePath);
                attachment.setStatus(1);

                attachmentsRepository.save(attachment);
                fileRoutes.add(filePath);
            } catch (Exception e) {
                System.err.println("Error al guardar el archivo: " + file.getOriginalFilename() + " - " + e.getMessage());
            }
        }

        return fileRoutes;
    }

    @Override
    protected IGenericRepository<Attachments, Long> getRepo() {
        return attachmentsRepository;
    }

    @Override
    public List<Attachments> findAttachmentsByMailAndStatus(Long mailId, int excludedStatus) {
        return attachmentsRepository.findByMailIdAndStatusNot(mailId, excludedStatus);
    }


}
