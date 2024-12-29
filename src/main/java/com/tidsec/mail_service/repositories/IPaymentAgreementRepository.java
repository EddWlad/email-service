package com.tidsec.mail_service.repositories;

import com.tidsec.mail_service.entities.PaymentAgreement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IPaymentAgreementRepository extends JpaRepository<PaymentAgreement, Long> {
    List<PaymentAgreement> findByStatusNot(Integer status);
}
