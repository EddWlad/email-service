package com.tidsec.mail_service.controller;

import com.tidsec.mail_service.entities.Supplier;
import com.tidsec.mail_service.model.SupplierDTO;
import com.tidsec.mail_service.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/supplier")
public class SupplierController {
    @Autowired
    private ISupplierService supplierService;

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
    public ResponseEntity<?> saveSupplier(@RequestBody SupplierDTO supplierDTO) throws URISyntaxException {
        if (supplierDTO.getName() == null || supplierDTO.getName().isBlank()) {
            return ResponseEntity.badRequest().body("El nombre del proveedor es necesario");
        }
        supplierService.saveSupplier(Supplier.builder()
                .id(supplierDTO.getId())
                .ruc(supplierDTO.getRuc())
                .name(supplierDTO.getName())
                .status(supplierDTO.getStatus())
                .email(supplierDTO.getEmail())
                .build());
        return ResponseEntity.created(new URI("/api/supplier/save")).build();
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

            supplierService.updateSupplier(id, supplier);
            return ResponseEntity.ok("Proveedor actualizado exitosamente");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSupplier(@PathVariable Long id){
        boolean result = supplierService.deleteSupplier(id);
        if(result){
            return ResponseEntity.ok("Proveedor eliminado correctamente");
        }else{
            return ResponseEntity.badRequest().body("Error al intentar eliminar al proveedor");
        }
    }
}
