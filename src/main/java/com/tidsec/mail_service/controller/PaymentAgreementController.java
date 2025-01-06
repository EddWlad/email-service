package com.tidsec.mail_service.controller;


import com.tidsec.mail_service.entities.PaymentAgreement;
import com.tidsec.mail_service.model.PaymentAgreementDTO;
import com.tidsec.mail_service.service.IPaymentAgreementService;
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

@RestController
@RequestMapping("api/paymentAgreement")
@RequiredArgsConstructor
public class PaymentAgreementController {

    private final IPaymentAgreementService paymentAgreementService;

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        List<PaymentAgreementDTO> agreements = paymentAgreementService.getAll()
                .stream()
                .map(agreement -> PaymentAgreementDTO.builder()
                        .id(agreement.getId())
                        .name(agreement.getName())
                        .description(agreement.getDescription())
                        .status(agreement.getStatus())
                        .build())
                .toList();
        return ResponseEntity.ok(agreements);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<PaymentAgreement> agreementOptional = paymentAgreementService.findById(id);
        if (agreementOptional.isPresent()) {
            PaymentAgreement agreement = agreementOptional.get();
            PaymentAgreementDTO agreementDTO = PaymentAgreementDTO.builder()
                    .id(agreement.getId())
                    .name(agreement.getName())
                    .description(agreement.getDescription())
                    .status(agreement.getStatus())
                    .build();
            return ResponseEntity.ok(agreementDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveAgreement(@RequestBody PaymentAgreementDTO agreementDTO) {
        if (agreementDTO.getName() == null || agreementDTO.getName().isBlank()) {
            return ResponseEntity.badRequest().body("El nombre del acuerdo de pago es necesario");
        }
        PaymentAgreement obj = paymentAgreementService.save(PaymentAgreement.builder()
                .id(agreementDTO.getId())
                .name(agreementDTO.getName())
                .description(agreementDTO.getDescription())
                .status(agreementDTO.getStatus())
                .build());

        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("{id}").buildAndExpand(obj.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAgreement(@PathVariable Long id, @RequestBody PaymentAgreementDTO agreementDTO) {
        Optional<PaymentAgreement> agreementOptional = paymentAgreementService.findById(id);
        if (agreementOptional.isPresent()) {
            PaymentAgreement agreement = agreementOptional.get();
            agreement.setName(agreementDTO.getName());
            agreement.setDescription(agreementDTO.getDescription());
            agreement.setStatus(agreementDTO.getStatus());

            paymentAgreementService.update(id, agreement);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Forma de pago actualizada exitosamente");
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAgreement(@PathVariable Long id) {
        boolean result = paymentAgreementService.delete(id);
        Map<String, String> response = new HashMap<>();
        if (result) {
            response.put("message", "Forma de pago eliminada correctamente");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Error al intentar eliminar la forma de pago");
            return ResponseEntity.badRequest().body(response);
        }
    }
}
