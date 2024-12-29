package com.tidsec.mail_service.controller;

import com.tidsec.mail_service.entities.MailingGroup;
import com.tidsec.mail_service.entities.Recipients;
import com.tidsec.mail_service.model.MailingGroupDTO;
import com.tidsec.mail_service.service.IMailingGroupService;
import com.tidsec.mail_service.service.IRecipientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/mailingGroup")
public class MailingGroupController {
    @Autowired
    private IMailingGroupService mailingGroupService;

    @Autowired
    private IRecipientsService recipientsService;

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        List<MailingGroupDTO> groupList = mailingGroupService.getAll()
                .stream()
                .map(group -> MailingGroupDTO.builder()
                        .id(group.getId())
                        .nameGroup(group.getNameGroup())
                        .description(group.getDescription())
                        .status(group.getStatus())
                        .recipientIds(group.getRecipients()
                                .stream()
                                .map(Recipients::getId)
                                .collect(Collectors.toList()))
                        .build())
                .toList();
        return ResponseEntity.ok(groupList);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<MailingGroup> groupOptional = mailingGroupService.findById(id);
        if (groupOptional.isPresent()) {
            MailingGroup group = groupOptional.get();
            MailingGroupDTO groupDTO = MailingGroupDTO.builder()
                    .id(group.getId())
                    .nameGroup(group.getNameGroup())
                    .description(group.getDescription())
                    .status(group.getStatus())
                    .recipientIds(group.getRecipients()
                            .stream()
                            .map(Recipients::getId)
                            .collect(Collectors.toList()))
                    .build();
            return ResponseEntity.ok(groupDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveMailingGroup(@RequestBody MailingGroupDTO groupDTO) throws URISyntaxException {
        if (groupDTO.getNameGroup() == null || groupDTO.getNameGroup().isBlank()) {
            return ResponseEntity.badRequest().body("El nombre del grupo es obligatorio");
        }

        List<Recipients> recipients = groupDTO.getRecipientIds()
                .stream()
                .map(recipientsService::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        MailingGroup mailingGroup = MailingGroup.builder()
                .id(groupDTO.getId())
                .nameGroup(groupDTO.getNameGroup())
                .description(groupDTO.getDescription())
                .status(groupDTO.getStatus())
                .recipients(recipients)
                .build();

        mailingGroupService.saveMailingGroup(mailingGroup);
        return ResponseEntity.created(new URI("/api/mailingGroup/save")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMailingGroup(@PathVariable Long id, @RequestBody MailingGroupDTO groupDTO) {
        Optional<MailingGroup> groupOptional = mailingGroupService.findById(id);
        if (groupOptional.isPresent()) {
            MailingGroup group = groupOptional.get();
            group.setNameGroup(groupDTO.getNameGroup());
            group.setDescription(groupDTO.getDescription());
            group.setStatus(groupDTO.getStatus());

            List<Recipients> updatedRecipients = groupDTO.getRecipientIds()
                    .stream()
                    .map(recipientsService::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());

            group.setRecipients(updatedRecipients);
            mailingGroupService.updateMailingGroup(id, group);
            return ResponseEntity.ok("Grupo de mailing actualizado exitosamente");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMailingGroup(@PathVariable Long id) {
        boolean result = mailingGroupService.deleteMailingGroup(id);
        if (result) {
            return ResponseEntity.ok("Grupo de mailing eliminado correctamente");
        } else {
            return ResponseEntity.badRequest().body("Error al intentar eliminar el grupo de mailing");
        }
    }
}
