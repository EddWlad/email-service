package com.tidsec.mail_service.controller;

import com.tidsec.mail_service.entities.Supplier;
import com.tidsec.mail_service.model.SupplierDTO;
import com.tidsec.mail_service.service.ISupplierService;
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
@RequestMapping("api/supplier")
@RequiredArgsConstructor
public class SupplierController {

    private final ISupplierService supplierService;
    private final ModelMapper modelMapper;

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        List<SupplierDTO> supplierList = supplierService.getAll()
                .stream()
                .map((Supplier obj) -> convertToDto(Optional.ofNullable(obj)))
                .toList();
        return ResponseEntity.ok(supplierList);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Supplier> supplierOptional = supplierService.findById(id);
        if (supplierOptional.isPresent()) {

            return ResponseEntity.ok(convertToDto(supplierOptional));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveSupplier(@RequestBody SupplierDTO supplierDTO) {
        if (supplierDTO.getName() == null || supplierDTO.getName().isBlank()) {
            return ResponseEntity.badRequest().body("El nombre del proveedor es necesario");
        }
        Supplier obj = supplierService.save(convertToEntity(supplierDTO));

        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("{id}").buildAndExpand(obj.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateSupplier(@PathVariable Long id, @RequestBody SupplierDTO supplierDTO) {
        Optional<Supplier> supplierOptional = supplierService.findById(id);
        if (supplierOptional.isPresent()) {
            Supplier obj = supplierService.update(id, convertToEntity(supplierDTO));

            Map<String, String> response = new HashMap<>();
            response.put("message", "Proveedor actualizado exitosamente");

            return ResponseEntity.ok(convertToDto(Optional.ofNullable(obj)));
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

    private SupplierDTO convertToDto(Optional<Supplier> obj){
        return modelMapper.map(obj, SupplierDTO.class);
    }

    private Supplier convertToEntity(SupplierDTO dto){
        return modelMapper.map(dto, Supplier.class);
    }
}
