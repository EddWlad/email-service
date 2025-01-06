package com.tidsec.mail_service.service;

import com.tidsec.mail_service.entities.*;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface IMailService extends IGenericService<Mail, Long>{
    /*List<Mail> getAll();
    Optional<Mail> findById(Long id);
    Mail saveMail(Mail mail);
    Mail updateMail(Long id, Mail mail);
    public boolean deleteMail(Long id);
    Long countMail();*/

    void sendMailWithFile(Long mailId, User user, Recipients recipients, MailingGroup copyRecipients, Supplier supplier,
                  Project project, PaymentAgreement paymentAgreement, Integer priority, String bill, String observation);
    void sendMail(String toRecipients, List<String> copyRecipients, File file, String subject, String message);

}
