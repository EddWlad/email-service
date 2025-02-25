package com.tidsec.mail_service.service;

import com.tidsec.mail_service.entities.ResetMail;

public interface IResetMailService {
    ResetMail findByRandom(String random);
    void save(ResetMail resetMail);
    void delete(ResetMail resetMail);
}
