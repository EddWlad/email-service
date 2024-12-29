package com.tidsec.mail_service.service.impl;


import com.tidsec.mail_service.entities.PaymentAgreement;
import com.tidsec.mail_service.repositories.IPaymentAgreementRepository;
import com.tidsec.mail_service.service.IPaymentAgreementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentAgreementServiceImpl implements IPaymentAgreementService {

    @Autowired
    private IPaymentAgreementRepository paymentAgreementRepository;

    @Override
    public List<PaymentAgreement> getAll() {
        return paymentAgreementRepository.findByStatusNot(0) ;
    }

    @Override
    public Optional<PaymentAgreement> findById(Long id) {
        return paymentAgreementRepository.findById(id);
    }

    @Override
    public PaymentAgreement savePaymentAgreement(PaymentAgreement paymentAgreement) {
        return paymentAgreementRepository.save(paymentAgreement);
    }

    @Override
    public PaymentAgreement updatePaymentAgreement(Long id, PaymentAgreement paymentAgreement) {
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
    public boolean deletePaymentAgreement(Long id) {
        PaymentAgreement paymentAgreementdb = paymentAgreementRepository.findById(id).orElse(null);
        if(paymentAgreementdb != null){
            paymentAgreementdb.setStatus(0);
            paymentAgreementRepository.save(paymentAgreementdb);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Long countPaymentAgreement() {
        return paymentAgreementRepository.count();
    }
}
