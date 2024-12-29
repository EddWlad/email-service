package com.tidsec.mail_service.controller;

import com.tidsec.mail_service.entities.Recipients;
import com.tidsec.mail_service.model.RecipientsDTO;
import com.tidsec.mail_service.service.IRecipientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/recipients")
public class RecipientsController {
    @Autowired
    private IRecipientsService recipientsService;

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        List<RecipientsDTO> recipientsList = recipientsService.getAll()
                .stream()
                .map(recipient -> RecipientsDTO.builder()
                        .id(recipient.getId())
                        .name(recipient.getName())
                        .lastName(recipient.getLastName())
                        .email(recipient.getEmail())
                        .phone(recipient.getPhone())
                        .status(recipient.getStatus())
                        .build())
                .toList();
        return ResponseEntity.ok(recipientsList);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Recipients> recipientOptional = recipientsService.findById(id);
        if (recipientOptional.isPresent()) {
            Recipients recipient = recipientOptional.get();
            RecipientsDTO recipientsDTO = RecipientsDTO.builder()
                    .id(recipient.getId())
                    .name(recipient.getName())
                    .lastName(recipient.getLastName())
                    .email(recipient.getEmail())
                    .phone(recipient.getPhone())
                    .status(recipient.getStatus())
                    .build();
            return ResponseEntity.ok(recipientsDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveRecipient(@RequestBody RecipientsDTO recipientsDTO) throws URISyntaxException {
        if (recipientsDTO.getName() == null || recipientsDTO.getName().isBlank()) {
            return ResponseEntity.badRequest().body("El nombre del destinatario es necesario");
        }
        if (recipientsDTO.getEmail() == null || recipientsDTO.getEmail().isBlank()) {
            return ResponseEntity.badRequest().body("El correo electrónico es obligatorio");
        }
        recipientsService.saveRecipients(Recipients.builder()
                .id(recipientsDTO.getId())
                .name(recipientsDTO.getName())
                .lastName(recipientsDTO.getLastName())
                .email(recipientsDTO.getEmail())
                .phone(recipientsDTO.getPhone())
                .status(recipientsDTO.getStatus())
                .build());
        return ResponseEntity.created(new URI("/api/recipients/save")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRecipient(@PathVariable Long id, @RequestBody RecipientsDTO recipientsDTO) {
        Optional<Recipients> recipientOptional = recipientsService.findById(id);
        if (recipientOptional.isPresent()) {
            Recipients recipient = recipientOptional.get();
            recipient.setName(recipientsDTO.getName());
            recipient.setLastName(recipientsDTO.getLastName());
            recipient.setEmail(recipientsDTO.getEmail());
            recipient.setPhone(recipientsDTO.getPhone());
            recipient.setStatus(recipientsDTO.getStatus());

            recipientsService.updateRecipients(id, recipient);
            return ResponseEntity.ok("Destinatario actualizado exitosamente");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRecipient(@PathVariable Long id) {
        boolean result = recipientsService.deleteRecipients(id);
        if (result) {
            return ResponseEntity.ok("Destinatario eliminado correctamente");
        } else {
            return ResponseEntity.badRequest().body("Error al intentar eliminar al destinatario");
        }
    }
}
