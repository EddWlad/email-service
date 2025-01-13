package com.tidsec.mail_service.controller;


import com.tidsec.mail_service.entities.PaymentAgreement;
import com.tidsec.mail_service.model.PaymentAgreementDTO;
import com.tidsec.mail_service.service.IPaymentAgreementService;
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
@RequestMapping("api/paymentAgreement")
@RequiredArgsConstructor
public class PaymentAgreementController {

    private final IPaymentAgreementService paymentAgreementService;
    private final ModelMapper modelMapper;

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        List<PaymentAgreementDTO> agreements = paymentAgreementService.getAll()
                .stream()
                .map((PaymentAgreement obj) -> convertToDto(Optional.ofNullable(obj)))
                .toList();
        return ResponseEntity.ok(agreements);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<PaymentAgreement> agreementOptional = paymentAgreementService.findById(id);
        if (agreementOptional.isPresent()) {

            return ResponseEntity.ok(convertToDto(agreementOptional));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveAgreement(@RequestBody PaymentAgreementDTO agreementDTO) {
        if (agreementDTO.getName() == null || agreementDTO.getName().isBlank()) {
            return ResponseEntity.badRequest().body("El nombre del acuerdo de pago es necesario");
        }
        PaymentAgreement obj = paymentAgreementService.save(convertToEntity(agreementDTO));

        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("{id}").buildAndExpand(obj.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAgreement(@PathVariable Long id, @RequestBody PaymentAgreementDTO agreementDTO) {
        Optional<PaymentAgreement> agreementOptional = paymentAgreementService.findById(id);
        if (agreementOptional.isPresent()) {
            PaymentAgreement obj = paymentAgreementService.update(id, convertToEntity(agreementDTO));

            Map<String, String> response = new HashMap<>();
            response.put("message", "Forma de pago actualizada exitosamente");

            return ResponseEntity.ok(convertToDto(Optional.ofNullable(obj)));
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

    private  PaymentAgreementDTO convertToDto(Optional<PaymentAgreement> obj){
        return modelMapper.map(obj, PaymentAgreementDTO.class);
    }

    private PaymentAgreement convertToEntity(PaymentAgreementDTO dto){
        return modelMapper.map(dto, PaymentAgreement.class);
    }
}
