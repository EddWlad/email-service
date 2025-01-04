package com.tidsec.mail_service.controller;

import com.tidsec.mail_service.entities.Attachments;
import com.tidsec.mail_service.model.AttachmentsDTO;
import com.tidsec.mail_service.service.IAttachmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/attachment")
public class AttachmentsController {
    @Autowired
    private IAttachmentsService attachmentsService;

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        List<AttachmentsDTO> attachmentsList = attachmentsService.getAll()
                .stream()
                .map(attachments -> AttachmentsDTO.builder()
                        .id(attachments.getId())
                        .status(attachments.getStatus())
                        .mail(attachments.getMail())
                        .routeAttachment(attachments.getRouteAttachment())
                        .build())
                .toList();
        return ResponseEntity.ok(attachmentsList);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Attachments> attachmentsOptional = attachmentsService.findById(id);
        if (attachmentsOptional.isPresent()) {
            Attachments attachments = attachmentsOptional.get();
            AttachmentsDTO attachmentsDTO = AttachmentsDTO.builder()
                    .id(attachments.getId())
                    .mail(attachments.getMail())
                    .status(attachments.getStatus())
                    .routeAttachment(attachments.getRouteAttachment())
                    .build();
            return ResponseEntity.ok(attachmentsDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveAttachments(@RequestBody AttachmentsDTO attachmentsDTO) throws URISyntaxException {
        if (attachmentsDTO.getRouteAttachment() == null || attachmentsDTO.getRouteAttachment().isBlank()) {
            return ResponseEntity.badRequest().body("La ruta del archivo es obligatorio");
        }
        attachmentsService.saveAttachments(Attachments.builder()
                .id(attachmentsDTO.getId())
                .mail(attachmentsDTO.getMail())
                .status(attachmentsDTO.getStatus())
                .routeAttachment(attachmentsDTO.getRouteAttachment())
                .build());
        return ResponseEntity.created(new URI("api/attachment/save")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAttachments(@PathVariable Long id, @RequestBody AttachmentsDTO attachmentsDTO){
        Optional<Attachments> attachmentsOptional = attachmentsService.findById(id);
        if(attachmentsOptional.isPresent()){
            Attachments attachments = attachmentsOptional.get();
            attachments.setRouteAttachment(attachmentsDTO.getRouteAttachment());
            attachments.setStatus(attachmentsDTO.getStatus());
            attachments.setMail(attachmentsDTO.getMail());

            attachmentsService.updateAttachments(id,attachments);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Archivo adjunto actualizado exitosamente");
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAttachemnts(@PathVariable Long id){
        boolean result = attachmentsService.deleteAttachments(id);
        Map<String, String> response = new HashMap<>();
        if (result) {
            response.put("message", "Archivo adjunto eliminado correctamente");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Error al intentar eliminar el archivo adjunto");
            return ResponseEntity.badRequest().body(response);
        }
    }

}