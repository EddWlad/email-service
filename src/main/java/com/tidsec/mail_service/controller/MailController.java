package com.tidsec.mail_service.controller;

import com.tidsec.mail_service.entities.*;

import com.tidsec.mail_service.model.MailDTO;
import com.tidsec.mail_service.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.net.URISyntaxException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/mail")
@RequiredArgsConstructor
public class MailController {

    private final IMailService mailService;

    private final IUserService userService;

    private final IRecipientsService recipientsService;

    private final IMailingGroupService mailingGroupService;

    private final ISupplierService supplierService;

    private final IPaymentAgreementService paymentAgreementService;

    private final IProjectService projectService;

    private final ModelMapper modelMapper;

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        List<MailDTO> mailList = mailService.getAll()
                .stream()
                .map((Mail obj)-> convertToDto(Optional.ofNullable(obj)))
                .toList();
        return ResponseEntity.ok(mailList);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Mail> mailOptional = mailService.findById(id);
        if (mailOptional.isPresent()) {

            return ResponseEntity.ok(convertToDto(mailOptional));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveMail(@RequestBody MailDTO mailDTO) {
        Mail obj = mailService.save(convertToEntity(mailDTO));

        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("{id}").buildAndExpand(obj.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMail(@PathVariable Long id, @RequestBody MailDTO mailDTO) {
        Optional<Mail> mailOptional = mailService.findById(id);
        if (mailOptional.isPresent()) {

            Mail obj = mailService.update(id, convertToEntity(mailDTO));

            Map<String, String> response = new HashMap<>();
            response.put("message", "Email actualizado exitosamente");

            return ResponseEntity.ok(convertToDto(Optional.ofNullable(obj)));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMail(@PathVariable Long id) {
        boolean result = mailService.delete(id);
        Map<String, String> response = new HashMap<>();
        if (result) {
            response.put("message", "Email eliminado correctamente");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Error al intentar eliminar el email");
            return ResponseEntity.badRequest().body(response);
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

    private MailDTO convertToDto(Optional<Mail> obj){
        return modelMapper.map(obj, MailDTO.class);
    }

    private Mail convertToEntity(MailDTO dto){
        return modelMapper.map(dto, Mail.class);
    }
}
