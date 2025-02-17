package com.tidsec.mail_service.repositories;

import com.tidsec.mail_service.entities.Signature;
import org.springframework.stereotype.Repository;

@Repository
public interface ISignatureRepository extends IGenericRepository<Signature, Long>{
}
