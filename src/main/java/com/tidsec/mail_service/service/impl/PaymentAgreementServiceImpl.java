package com.tidsec.mail_service.service.impl;


import com.tidsec.mail_service.entities.PaymentAgreement;
import com.tidsec.mail_service.entities.Project;
import com.tidsec.mail_service.exception.ModelNotFoundException;
import com.tidsec.mail_service.repositories.IGenericRepository;
import com.tidsec.mail_service.repositories.IPaymentAgreementRepository;
import com.tidsec.mail_service.service.IPaymentAgreementService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentAgreementServiceImpl extends GenericServiceImpl<PaymentAgreement, Long> implements IPaymentAgreementService {

    private final IPaymentAgreementRepository paymentAgreementRepository;

    @Override
    protected IGenericRepository<PaymentAgreement, Long> getRepo() {
        return paymentAgreementRepository;
    }

    /*@Override
    public List<PaymentAgreement> getAll() {
        return paymentAgreementRepository.findByStatusNot(0) ;
    }

    @Override
    public Optional<PaymentAgreement> findById(Long id) {
        return paymentAgreementRepository.findById(id)
                .filter(paymentAgreement -> paymentAgreement.getStatus() != 0)
                .or(() -> {
                    throw new ModelNotFoundException("ID NOT FOUND: " + id);
                });
    }

    @Override
    public PaymentAgreement save(PaymentAgreement paymentAgreement) {
        return paymentAgreementRepository.save(paymentAgreement);
    }

    @Override
    public PaymentAgreement update(Long id, PaymentAgreement paymentAgreement) {
        PaymentAgreement paymentAgreementdb = paymentAgreementRepository.findById(id).orElse(null);
        if(paymentAgreement != null){
            paymentAgreementdb.setName(paymentAgreement.getName());
            paymentAgreementdb.setDescription(paymentAgreement.getDescription());
            paymentAgreementdb.setStatus(paymentAgreement.getStatus());
            return paymentAgreementRepository.save(paymentAgreementdb);
        }else{
            return null;
        }
    }

    @Override
    public boolean delete(Long id) {
        PaymentAgreement paymentAgreementDb = paymentAgreementRepository.findById(id)
                .filter(paymentAgreement -> paymentAgreement.getStatus() != 0)
                .orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND OR INACTIVE: " + id));
        paymentAgreementDb.setStatus(0);
        paymentAgreementRepository.save(paymentAgreementDb);
        return true;
    }

    @Override
    public Long count() {
        return paymentAgreementRepository.count();
    }*/
}
