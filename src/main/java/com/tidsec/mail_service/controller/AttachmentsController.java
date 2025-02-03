package com.tidsec.mail_service.controller;

import com.tidsec.mail_service.entities.Attachments;
import com.tidsec.mail_service.model.AttachmentsDTO;
import com.tidsec.mail_service.service.IAttachmentsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/attachment")
@RequiredArgsConstructor
public class AttachmentsController {

    private final IAttachmentsService attachmentsService;
    private final ModelMapper modelMapper;

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        List<AttachmentsDTO> attachmentsList = attachmentsService.getAll()
                .stream()
                .map((Attachments obj) -> convertToDto(Optional.ofNullable(obj)))
                .toList();
        return ResponseEntity.ok(attachmentsList);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Attachments> attachmentsOptional = attachmentsService.findById(id);
        if (attachmentsOptional.isPresent()) {
            return ResponseEntity.ok(convertToDto(attachmentsOptional));
        }
        return ResponseEntity.notFound().build();
    }

    /*@PostMapping("/save")
    public ResponseEntity<?> saveAttachments(@RequestBody AttachmentsDTO attachmentsDTO) {
        if (attachmentsDTO.getRouteAttachment() == null || attachmentsDTO.getRouteAttachment().isBlank()) {
            return ResponseEntity.badRequest().body("La ruta del archivo es obligatorio");
        }
        Attachments obj = attachmentsService.save(convertToEntity(attachmentsDTO));

        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("{id}").buildAndExpand(obj.getId()).toUri();

        return ResponseEntity.created(location).build();
    }*/

    @PostMapping("/upload/{mailId}")
    public ResponseEntity<?> uploadAttachments(
            @PathVariable Long mailId,
            @RequestParam("files") List<MultipartFile> files) {
        if (files.isEmpty()) {
            return ResponseEntity.badRequest().body("No se enviaron archivos");
        }
        try {
            List<String> fileRoutes = attachmentsService.saveAttachments(mailId, files);
            return ResponseEntity.ok(fileRoutes);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al subir archivos: " + e.getMessage());
        }
    }




    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAttachments(@PathVariable Long id, @RequestBody AttachmentsDTO attachmentsDTO){
        Optional<Attachments> attachmentsOptional = attachmentsService.findById(id);
        if(attachmentsOptional.isPresent()){
            Attachments obj = attachmentsService.update(id,convertToEntity(attachmentsDTO));

            Map<String, String> response = new HashMap<>();
            response.put("message", "Archivo adjunto actualizado exitosamente");

            return ResponseEntity.ok(convertToDto(Optional.ofNullable(obj)));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAttachments(@PathVariable Long id) {
        if (attachmentsService.delete(id)) {
            return ResponseEntity.ok(Map.of("message", "Archivo adjunto eliminado correctamente"));
        }
        return ResponseEntity.notFound().build();
    }

    private AttachmentsDTO convertToDto(Optional<Attachments> obj){
        return  modelMapper.map(obj, AttachmentsDTO.class);
    }

    private Attachments convertToEntity(AttachmentsDTO dto){
        return  modelMapper.map(dto, Attachments.class);
    }
}