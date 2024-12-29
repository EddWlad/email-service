package com.tidsec.mail_service.controller;

import com.tidsec.mail_service.entities.*;
import com.tidsec.mail_service.model.AttachmentsDTO;
import com.tidsec.mail_service.model.MailDTO;
import com.tidsec.mail_service.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/mail")
public class MailController {
    @Autowired
    private IMailService mailService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRecipientsService recipientsService;

    @Autowired
    private IAttachmentsService attachmentsService;

    @Autowired
    private IMailingGroupService mailingGroupService;

    @Autowired
    private ISupplierService supplierService;

    @Autowired
    private IPaymentAgreementService paymentAgreementService;

    @Autowired
    private IProjectService projectService;

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        List<MailDTO> mailList = mailService.getAll()
                .stream()
                .map(mail -> MailDTO.builder()
                        .id(mail.getId())
                        .idRecipients(mail.getIdRecipients())
                        .mailingGroup(mail.getMailingGroup())
                        .bill(mail.getBill())
                        .priority(mail.getPriority())
                        .supplier(mail.getSupplier())
                        .project(mail.getProject())
                        .paymentAgreement(mail.getPaymentAgreement())
                        .dateCreate(mail.getDateCreate())
                        .observation(mail.getObservation())
                        .status(mail.getStatus())
                        .build())
                .toList();
        return ResponseEntity.ok(mailList);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Mail> mailOptional = mailService.findById(id);
        if (mailOptional.isPresent()) {
            Mail mail = mailOptional.get();
            MailDTO mailDTO = MailDTO.builder()
                    .id(mail.getId())
                    .idRecipients(mail.getIdRecipients())
                    .mailingGroup(mail.getMailingGroup())
                    .bill(mail.getBill())
                    .priority(mail.getPriority())
                    .supplier(mail.getSupplier())
                    .project(mail.getProject())
                    .paymentAgreement(mail.getPaymentAgreement())
                    .dateCreate(mail.getDateCreate())
                    .observation(mail.getObservation())
                    .status(mail.getStatus())
                    .build();
            return ResponseEntity.ok(mailDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveMail(@RequestBody MailDTO mailDTO) throws URISyntaxException {
        mailService.saveMail(Mail.builder()
                .id(mailDTO.getId())
                .idRecipients(mailDTO.getIdRecipients())
                .mailingGroup(mailDTO.getMailingGroup())
                .bill(mailDTO.getBill())
                .priority(mailDTO.getPriority())
                .supplier(mailDTO.getSupplier())
                .project(mailDTO.getProject())
                .paymentAgreement(mailDTO.getPaymentAgreement())
                .dateCreate(mailDTO.getDateCreate())
                .observation(mailDTO.getObservation())
                .status(mailDTO.getStatus())
                .build());
        return ResponseEntity.created(new URI("/api/mail/save")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMail(@PathVariable Long id, @RequestBody MailDTO mailDTO) {
        Optional<Mail> mailOptional = mailService.findById(id);
        if (mailOptional.isPresent()) {
            Mail mail = mailOptional.get();
            mail.setIdRecipients(mailDTO.getIdRecipients());
            mail.setMailingGroup(mailDTO.getMailingGroup());
            mail.setBill(mailDTO.getBill());
            mail.setPriority(mailDTO.getPriority());
            mail.setSupplier(mailDTO.getSupplier());
            mail.setProject(mailDTO.getProject());
            mail.setPaymentAgreement(mailDTO.getPaymentAgreement());
            mail.setDateCreate(mailDTO.getDateCreate());
            mail.setObservation(mailDTO.getObservation());
            mail.setStatus(mailDTO.getStatus());

            mailService.updateMail(id, mail);
            return ResponseEntity.ok("Correo actualizado exitosamente");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMail(@PathVariable Long id) {
        boolean result = mailService.deleteMail(id);
        if (result) {
            return ResponseEntity.ok("Correo eliminado correctamente");
        } else {
            return ResponseEntity.badRequest().body("Error al intentar eliminar el correo");
        }
    }

    @PostMapping("/sendMessage/{userId}")
    public ResponseEntity<?> receiveRequestEmail(@RequestBody MailDTO mailDTO, @PathVariable Long userId) {
        User userFrom = userService.findById(userId).orElse(null);
        if (userFrom == null) {
            return ResponseEntity.badRequest().body("El usuario remitente no existe");
        }

        if (mailDTO.getIdRecipients() == null) {
            return ResponseEntity.badRequest().body("El ID del destinatario es obligatorio");
        }

        Recipients recipients = recipientsService.findById(mailDTO.getIdRecipients()).orElse(null);
        if (recipients == null) {
            return ResponseEntity.badRequest().body("El destinatario no existe");
        }

        if (mailDTO.getMailingGroup() == null || mailDTO.getMailingGroup().getId() == null) {
            return ResponseEntity.badRequest().body("El ID del grupo de correo es obligatorio");
        }

        MailingGroup mailingGroup = mailingGroupService.findById(mailDTO.getMailingGroup().getId()).orElse(null);
        if (mailingGroup == null) {
            return ResponseEntity.badRequest().body("El grupo de correo no existe");
        }

        if (mailDTO.getSupplier() == null || mailDTO.getSupplier().getId() == null) {
            return ResponseEntity.badRequest().body("El ID del proveedor es obligatorio");
        }

        Supplier supplier = supplierService.findById(mailDTO.getSupplier().getId()).orElse(null);
        if (supplier == null) {
            return ResponseEntity.badRequest().body("El proveedor no existe");
        }

        if (mailDTO.getProject() == null || mailDTO.getProject().getId() == null) {
            return ResponseEntity.badRequest().body("El ID del proyecto es obligatorio");
        }

        Project project = projectService.findById(mailDTO.getProject().getId()).orElse(null);
        if (project == null) {
            return ResponseEntity.badRequest().body("El proyecto no existe");
        }

        if (mailDTO.getPaymentAgreement() == null || mailDTO.getPaymentAgreement().getId() == null) {
            return ResponseEntity.badRequest().body("El ID del acuerdo de pago es obligatorio");
        }

        PaymentAgreement paymentAgreement = paymentAgreementService.findById(mailDTO.getPaymentAgreement().getId()).orElse(null);
        if (paymentAgreement == null) {
            return ResponseEntity.badRequest().body("El acuerdo de pago no existe");
        }

        try {
            mailService.sendMailWithFile(
                    mailDTO.getId(),
                    userFrom,
                    recipients,
                    mailingGroup,
                    supplier,
                    project,
                    paymentAgreement,
                    mailDTO.getPriority(),
                    mailDTO.getBill(),
                    mailDTO.getObservation()
            );

            return ResponseEntity.ok(Map.of("estado", "Enviado", "mensaje", "Correo enviado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al enviar el correo: " + e.getMessage());
        }
    }
}
