package com.tidsec.mail_service.repositories;

import com.tidsec.mail_service.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ISupplierRepository extends JpaRepository<Supplier, Long> {
    List<Supplier> findByStatusNot(Integer status);
}
