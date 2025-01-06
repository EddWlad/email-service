package com.tidsec.mail_service.controller;

import com.tidsec.mail_service.entities.MailingGroup;
import com.tidsec.mail_service.entities.Recipients;
import com.tidsec.mail_service.model.MailingGroupDTO;
import com.tidsec.mail_service.service.IMailingGroupService;
import com.tidsec.mail_service.service.IRecipientsService;
import lombok.RequiredArgsConstructor;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/mailingGroup")
@RequiredArgsConstructor
public class MailingGroupController {

    private final IMailingGroupService mailingGroupService;

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
    public ResponseEntity<?> saveMailingGroup(@RequestBody MailingGroupDTO groupDTO) {
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

        MailingGroup obj = mailingGroupService.save(mailingGroup);

        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("{id}").buildAndExpand(obj.getId()).toUri();

        return ResponseEntity.created(location).build();
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
            mailingGroupService.update(id, group);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Grupo de mail actualizado exitosamente");
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMailingGroup(@PathVariable Long id) {
        boolean result = mailingGroupService.delete(id);
        Map<String, String> response = new HashMap<>();
        if (result) {
            response.put("message", "Grupo de email eliminado correctamente");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Error al intentar eliminar el grupo de email");
            return ResponseEntity.badRequest().body(response);
        }
    }
}
