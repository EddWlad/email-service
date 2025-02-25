package com.tidsec.mail_service.service.impl;

import com.tidsec.mail_service.entities.ResetMail;
import com.tidsec.mail_service.repositories.IResetMailRepository;
import com.tidsec.mail_service.service.IResetMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResetMailServiceImpl implements IResetMailService {
    private final IResetMailRepository resetMailRepository;

    @Override
    public ResetMail findByRandom(String random) {
        return resetMailRepository.findByRandom(random);
    }

    @Override
    public void save(ResetMail resetMail) {
        resetMailRepository.save(resetMail);
    }

    @Override
    public void delete(ResetMail resetMail) {
        resetMailRepository.delete(resetMail);
    }
}
