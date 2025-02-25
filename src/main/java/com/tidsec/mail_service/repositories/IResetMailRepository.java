package com.tidsec.mail_service.repositories;

import com.tidsec.mail_service.entities.ResetMail;

public interface IResetMailRepository extends IGenericRepository<ResetMail, Long> {

    ResetMail findByRandom(String random);
}
