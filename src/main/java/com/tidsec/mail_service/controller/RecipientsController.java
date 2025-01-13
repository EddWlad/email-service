package com.tidsec.mail_service.controller;

import com.tidsec.mail_service.entities.Recipients;
import com.tidsec.mail_service.model.RecipientsDTO;
import com.tidsec.mail_service.service.IRecipientsService;
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
@RequestMapping("api/recipients")
@RequiredArgsConstructor
public class RecipientsController {

    private final IRecipientsService recipientsService;
    private final ModelMapper modelMapper;

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        List<RecipientsDTO> recipientsList = recipientsService.getAll()
                .stream()
                .map((Recipients obj) -> convertToDto((Optional.ofNullable(obj))))
                .toList();
        return ResponseEntity.ok(recipientsList);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Recipients> recipientOptional = recipientsService.findById(id);
        if (recipientOptional.isPresent()) {
            return ResponseEntity.ok(convertToDto(recipientOptional));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveRecipient(@RequestBody RecipientsDTO recipientsDTO) {
        if (recipientsDTO.getName() == null || recipientsDTO.getName().isBlank()) {
            return ResponseEntity.badRequest().body("El nombre del destinatario es necesario");
        }
        if (recipientsDTO.getEmail() == null || recipientsDTO.getEmail().isBlank()) {
            return ResponseEntity.badRequest().body("El correo electrónico es obligatorio");
        }
        Recipients obj = recipientsService.save(convertToEntity(recipientsDTO));

        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("{id}").buildAndExpand(obj.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRecipient(@PathVariable Long id, @RequestBody RecipientsDTO recipientsDTO) {
        Optional<Recipients> recipientOptional = recipientsService.findById(id);
        if (recipientOptional.isPresent()) {
            Recipients obj = recipientsService.update(id, convertToEntity(recipientsDTO));

            Map<String, String> response = new HashMap<>();
            response.put("message", "Destinatario actualizado exitosamente");

            return ResponseEntity.ok(convertToDto(Optional.ofNullable(obj)));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRecipient(@PathVariable Long id) {
        boolean result = recipientsService.delete(id);
        Map<String, String> response = new HashMap<>();
        if (result) {
            response.put("message", "Destinatario eliminado correctamente");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Error al intentar eliminar el destinatario");
            return ResponseEntity.badRequest().body(response);
        }
    }

    private RecipientsDTO convertToDto(Optional<Recipients> obj){
        return modelMapper.map(obj, RecipientsDTO.class);
    }

    private Recipients convertToEntity(RecipientsDTO dto){
        return modelMapper.map(dto, Recipients.class);
    }
}
