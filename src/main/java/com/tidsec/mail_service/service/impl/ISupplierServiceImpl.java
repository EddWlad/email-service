package com.tidsec.mail_service.service.impl;

import com.tidsec.mail_service.entities.Supplier;
import com.tidsec.mail_service.repositories.ISupplierRepository;
import com.tidsec.mail_service.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ISupplierServiceImpl implements ISupplierService {

    @Autowired
    private ISupplierRepository supplierRepository;

    @Override
    public List<Supplier> getAll() {
        return supplierRepository.findByStatusNot(0);
    }

    @Override
    public Optional<Supplier> findById(Long id) {
        return supplierRepository.findById(id);
    }

    @Override
    public Supplier saveSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    @Override
    public Supplier updateSupplier(Long id, Supplier supplier) {
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
    public boolean deleteSupplier(Long id) {
        Supplier supplierDb = supplierRepository.findById(id).orElse(null);
        if(supplierDb != null){
            supplierDb.setStatus(0);
            supplierRepository.save(supplierDb);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Long countSupplier() {
        return supplierRepository.count();
    }
}
