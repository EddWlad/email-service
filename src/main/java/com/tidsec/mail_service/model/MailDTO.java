package com.tidsec.mail_service.model;
import com.tidsec.mail_service.entities.MailingGroup;
import com.tidsec.mail_service.entities.PaymentAgreement;
import com.tidsec.mail_service.entities.Project;
import com.tidsec.mail_service.entities.Supplier;
import lombok.*;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MailDTO {
    private Long id;
    private Long idRecipients;
    private MailingGroup mailingGroup;
    private String bill;
    private Integer priority;
    private Supplier supplier;
    private Project project;
    private PaymentAgreement paymentAgreement;
    private Date dateCreate = new Date();
    private String observation;
    private Integer status;
}
