package com.tidsec.mail_service.service;

import com.tidsec.mail_service.entities.*;
import com.tidsec.mail_service.model.IMailProcDTO;
import com.tidsec.mail_service.model.MailProcDTO;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IMailService extends IGenericService<Mail, Long>{
    void sendMailWithFile(Long mailId, User user, Recipients recipients, MailingGroup copyRecipients, Supplier supplier,
                  Project project, PaymentAgreement paymentAgreement, Integer priority, String bill, String observation);
    void sendMail(String toRecipients, List<String> copyRecipients, File file, String subject, String message);

    List<Mail> search(String bill, String observation);
    List<Mail> searchByDates(LocalDateTime date1, LocalDateTime date2);
    List<IMailProcDTO> callProcedureOrFunctionProjection();

    List<MailProcDTO> callProcedureOrFunctionNative();
}
