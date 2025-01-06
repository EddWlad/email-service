package com.tidsec.mail_service.service.impl;

import com.tidsec.mail_service.entities.Supplier;
import com.tidsec.mail_service.exception.ModelNotFoundException;
import com.tidsec.mail_service.repositories.IGenericRepository;
import com.tidsec.mail_service.repositories.ISupplierRepository;
import com.tidsec.mail_service.service.ISupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl extends GenericServiceImpl<Supplier, Long> implements ISupplierService {

    private final ISupplierRepository supplierRepository;

    @Override
    protected IGenericRepository<Supplier, Long> getRepo() {
        return supplierRepository;
    }

    /*@Override
    public List<Supplier> getAll() {
        return supplierRepository.findByStatusNot(0);
    }

    @Override
    public Optional<Supplier> findById(Long id) {
        return supplierRepository.findById(id)
                .filter(supplier -> supplier.getStatus() != 0)
                .or(() -> {
                    throw new ModelNotFoundException("ID NOT FOUND: " + id);
                });
    }

    @Override
    public Supplier save(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    @Override
    public Supplier update(Long id, Supplier supplier) {
        Supplier supplierDb = supplierRepository.findById(id).orElse(null);
        if(supplier != null){
            supplierDb.setName(supplier.getName());
            supplierDb.setEmail(supplier.getEmail());
            supplierDb.setRuc(supplier.getRuc());
            supplierDb.setStatus(supplier.getStatus());
            return supplierRepository.save(supplierDb);
        }else{
            return null;
        }
    }

    @Override
    public boolean delete(Long id) {
        Supplier supplierDb = supplierRepository.findById(id)
                .filter(supplier -> supplier.getStatus() != 0)
                .orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND OR INACTIVE: " + id));
        supplierDb.setStatus(0);
        supplierRepository.save(supplierDb);
        return true;
    }

    @Override
    public Long count() {
        return supplierRepository.count();
    }*/
}
