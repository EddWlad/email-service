package com.tidsec.mail_service.controller;

import com.tidsec.mail_service.entities.Supplier;
import com.tidsec.mail_service.model.SupplierDTO;
import com.tidsec.mail_service.service.ISupplierService;
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
@RequestMapping("api/supplier")
@RequiredArgsConstructor
public class SupplierController {

    private final ISupplierService supplierService;

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        List<SupplierDTO> supplierList = supplierService.getAll()
                .stream()
                .map(supplier -> SupplierDTO.builder()
                        .id(supplier.getId())
                        .ruc(supplier.getRuc())
                        .name(supplier.getName())
                        .email(supplier.getEmail())
                        .status(supplier.getStatus())
                        .build())
                .toList();
        return ResponseEntity.ok(supplierList);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Supplier> supplierOptional = supplierService.findById(id);
        if (supplierOptional.isPresent()) {
            Supplier supplier = supplierOptional.get();
            SupplierDTO supplierDTO = SupplierDTO.builder()
                    .id(supplier.getId())
                    .ruc(supplier.getRuc())
                    .name(supplier.getName())
                    .email(supplier.getEmail())
                    .status(supplier.getStatus())
                    .build();
            return ResponseEntity.ok(supplierDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveSupplier(@RequestBody SupplierDTO supplierDTO) {
        if (supplierDTO.getName() == null || supplierDTO.getName().isBlank()) {
            return ResponseEntity.badRequest().body("El nombre del proveedor es necesario");
        }
        Supplier obj = supplierService.save(Supplier.builder()
                .id(supplierDTO.getId())
                .ruc(supplierDTO.getRuc())
                .name(supplierDTO.getName())
                .status(supplierDTO.getStatus())
                .email(supplierDTO.getEmail())
                .build());

        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("{id}").buildAndExpand(obj.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateSupplier(@PathVariable Long id, @RequestBody SupplierDTO supplierDTO) {
        Optional<Supplier> supplierOptional = supplierService.findById(id);
        if (supplierOptional.isPresent()) {
            Supplier supplier = supplierOptional.get();
            supplier.setRuc(supplierDTO.getRuc());
            supplier.setName(supplierDTO.getName());
            supplier.setEmail(supplierDTO.getEmail());
            supplier.setStatus(supplierDTO.getStatus());

            supplierService.update(id, supplier);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Proveedor actualizado exitosamente");
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSupplier(@PathVariable Long id){
        boolean result = supplierService.delete(id);
        Map<String, String> response = new HashMap<>();
        if (result) {
            response.put("message", "Proveedor eliminado correctamente");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Error al intentar eliminar el proveedor");
            return ResponseEntity.badRequest().body(response);
        }
    }
}
