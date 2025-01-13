package com.tidsec.mail_service.controller;

import com.tidsec.mail_service.entities.MailingGroup;
import com.tidsec.mail_service.entities.Recipients;
import com.tidsec.mail_service.model.MailingGroupDTO;
import com.tidsec.mail_service.service.IMailingGroupService;
import com.tidsec.mail_service.service.IRecipientsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    private final IRecipientsService recipientsService;

    private final ModelMapper modelMapper;

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        List<MailingGroupDTO> groupList = mailingGroupService.getAll()
                .stream()
                .map(this::convertToDto)
                .toList();
        return ResponseEntity.ok(groupList);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<MailingGroup> groupOptional = mailingGroupService.findById(id);
        return groupOptional.map(group -> ResponseEntity.ok(convertToDto(group)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveMailingGroup(@RequestBody MailingGroupDTO groupDTO) {
        if (groupDTO.getNameGroup() == null || groupDTO.getNameGroup().isBlank()) {
            return ResponseEntity.badRequest().body("El nombre del grupo es obligatorio");
        }

        MailingGroup mailingGroup = convertToEntity(groupDTO);
        MailingGroup savedGroup = mailingGroupService.save(mailingGroup);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedGroup.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMailingGroup(@PathVariable Long id, @RequestBody MailingGroupDTO groupDTO) {
        Optional<MailingGroup> groupOptional = mailingGroupService.findById(id);
        if (groupOptional.isPresent()) {
            MailingGroup updatedGroup = convertToEntity(groupDTO);
            updatedGroup.setId(id);
            mailingGroupService.update(id, updatedGroup);

            return ResponseEntity.ok(convertToDto(updatedGroup));
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

    private MailingGroupDTO convertToDto(MailingGroup mailingGroup) {
        MailingGroupDTO groupDTO = modelMapper.map(mailingGroup, MailingGroupDTO.class);

        groupDTO.setRecipientIds(
                mailingGroup.getRecipients()
                        .stream()
                        .map(Recipients::getId)
                        .collect(Collectors.toList())
        );

        groupDTO.setRecipientNames(
                mailingGroup.getRecipients()
                        .stream()
                        .map(Recipients::getName) // Asume que `Recipients` tiene un método `getName()`
                        .collect(Collectors.toList())
        );

        return groupDTO;
    }

    private MailingGroup convertToEntity(MailingGroupDTO groupDTO) {
        MailingGroup mailingGroup = modelMapper.map(groupDTO, MailingGroup.class);
        List<Recipients> recipients = groupDTO.getRecipientIds()
                .stream()
                .map(recipientsService::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        mailingGroup.setRecipients(recipients);
        return mailingGroup;
    }
}
