package com.tidsec.mail_service.controller;

import com.tidsec.mail_service.entities.*;

import com.tidsec.mail_service.model.FilterMailDTO;
import com.tidsec.mail_service.model.IMailProcDTO;
import com.tidsec.mail_service.model.MailDTO;
import com.tidsec.mail_service.model.MailProcDTO;
import com.tidsec.mail_service.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.net.URISyntaxException;

import java.time.LocalDateTime;
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
        return ResponseEntity.ok(obj);
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

    //Queries//
    @GetMapping("/search/dates")
    public ResponseEntity<List<MailDTO>> searchByDates(
            @RequestParam(value = "date1", defaultValue = "2025-01-02T00:00:00") String date1,
            @RequestParam(value = "date2", defaultValue = "2025-01-02T00:00:00") String date2
    ){
        List<Mail> mails = mailService.searchByDates(LocalDateTime.parse(date1), LocalDateTime.parse(date2));
        List<MailDTO> mailsDTO = modelMapper.map(mails, new TypeToken<List<MailDTO>>(){}.getType());
        return ResponseEntity.ok(mailsDTO);
    }

    @PostMapping("/search/others")
    public ResponseEntity<List<MailDTO>> searchByOthers(@RequestBody FilterMailDTO filterDTO){
        List<Mail> mails = mailService.search(filterDTO.getBill(), filterDTO.getObservation());
        List<MailDTO> mailsDTO = modelMapper.map(mails, new TypeToken<List<MailDTO>>(){}.getType());
        return ResponseEntity.ok(mailsDTO);
    }

    @GetMapping("/callProcedureProjection")
    public ResponseEntity<List<IMailProcDTO>> callProcedureProjection(){
        return ResponseEntity.ok(mailService.callProcedureOrFunctionProjection());
    }

    @GetMapping("/callProcedureNative")
    public ResponseEntity<List<MailProcDTO>> callProcedureNative(){
        return ResponseEntity.ok(mailService.callProcedureOrFunctionNative());
    }


    private MailDTO convertToDto(Optional<Mail> obj){
        return modelMapper.map(obj, MailDTO.class);
    }

    private Mail convertToEntity(MailDTO dto){
        return modelMapper.map(dto, Mail.class);
    }
}
