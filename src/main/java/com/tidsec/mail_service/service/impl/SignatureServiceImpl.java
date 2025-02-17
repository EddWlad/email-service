package com.tidsec.mail_service.service.impl;

import com.tidsec.mail_service.entities.Signature;
import com.tidsec.mail_service.repositories.IGenericRepository;
import com.tidsec.mail_service.repositories.ISignatureRepository;
import com.tidsec.mail_service.service.ISignatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignatureServiceImpl extends GenericServiceImpl<Signature, Long> implements ISignatureService {
    private final ISignatureRepository signatureRepository;

    @Override
    protected IGenericRepository<Signature, Long> getRepo(){return signatureRepository;}
}
