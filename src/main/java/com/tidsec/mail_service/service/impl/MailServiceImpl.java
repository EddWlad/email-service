package com.tidsec.mail_service.service.impl;

import com.tidsec.mail_service.entities.*;
import com.tidsec.mail_service.repositories.IMailRepository;
import com.tidsec.mail_service.service.IAttachmentsService;
import com.tidsec.mail_service.service.IMailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MailServiceImpl implements IMailService {
    @Autowired
    private IMailRepository mailRepository;

    @Autowired
    private IAttachmentsService attachmentsService;
    @Autowired
    private JavaMailSender mailSender;
    @Override
    public List<Mail> getAll() {
        return mailRepository.findByStatusNot(0);
    }

    @Override
    public Optional<Mail> findById(Long id) {
        return mailRepository.findById(id);
    }

    @Override
    public Mail saveMail(Mail mail) {
        return mailRepository.save(mail);
    }

    @Override
    public Mail updateMail(Long id, Mail mail) {
        Mail mailDb = mailRepository.findById(id).orElse(null);
        if(mail != null){
            mailDb.setIdRecipients(mail.getIdRecipients());
            mailDb.setMailingGroup(mail.getMailingGroup());
            mailDb.setBill(mail.getBill());
            mailDb.setPriority(mail.getPriority());
            mailDb.setProject((mail.getProject()));
            mailDb.setSupplier(mail.getSupplier());
            mailDb.setPaymentAgreement(mail.getPaymentAgreement());
            mailDb.setDateCreate(mail.getDateCreate());
            mailDb.setAttachments(mail.getAttachments());
            mailDb.setObservation(mail.getObservation());
            mailDb.setStatus(mail.getStatus());
            return mailRepository.save(mailDb);
        }
        else{
            return null;
        }
    }

    @Override
    public boolean deleteMail(Long id) {
        Mail mailDb = mailRepository.findById(id).orElse(null);
        if(mailDb != null){
            mailDb.setStatus(0);
            mailRepository.save(mailDb);
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public Long countMail() {
        return mailRepository.count();
    }

    @Override
    public void sendMailWithFile(Long mailId, User user, Recipients toRecipients, MailingGroup mailingGroup, Supplier supplier, Project project,
                         PaymentAgreement paymentAgreement, Integer priority, String bill, String observation) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());

            mimeMessageHelper.setFrom(user.getEmail());
            mimeMessageHelper.setTo(toRecipients.getEmail());

            List<String> copyRecipients = mailingGroup.getRecipients()
                    .stream()
                    .map(Recipients::getEmail)
                    .collect(Collectors.toList());

            mimeMessageHelper.setCc(copyRecipients.toArray(new String[0]));

            String body;
            if (priority == 1) {
                mimeMessageHelper.setSubject("PAGO URGENTE//" + "FACTURA: " + bill + "//" + supplier.getName() + "//" + project.getCompany() + "-" + project.getName());
                body = "<p>Estimado/a Ing. " + toRecipients.getName() + ",</p>" +
                        "<p>Su ayuda con el pago y retención a " + paymentAgreement.getDescription() + ", "+ observation + ", "+ "gracias.</p>";
            } else {
                mimeMessageHelper.setSubject("FACTURA: " + bill + "//" + supplier.getName() + "//" + project.getCompany() + "-" + project.getName());
                body = "<p>Estimado/a Ing. " + toRecipients.getName() + ",</p>" +
                        "<p>Su ayuda con el pago y retención a " + paymentAgreement.getDescription() + ", gracias.</p>";
            }

            mimeMessageHelper.setText(body, true);

            List<Attachments> attachments = attachmentsService.findAttachmentsByMailAndStatus(mailId, 0);

            if (attachments != null && !attachments.isEmpty()) {
                for (Attachments attachment : attachments) {
                    File file = new File(attachment.getRouteAttachment());
                    mimeMessageHelper.addAttachment(file.getName(), file);
                }
            }

            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void sendMail(String toRecipients, List<String> copyRecipients, File file, String subject, String message) {

    }
}
