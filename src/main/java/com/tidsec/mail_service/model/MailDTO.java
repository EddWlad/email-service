package com.tidsec.mail_service.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.tidsec.mail_service.entities.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MailDTO {
    @EqualsAndHashCode.Include
    private Long id;
    private Long idRecipients;
    private MailingGroup mailingGroup;
    private String bill;
    private Integer priority;
    private Supplier supplier;
    private Project project;
    private PaymentAgreement paymentAgreement;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateCreate;
    private String observation;
    private Integer status;
    private List<Attachments> attachments = new ArrayList<Attachments>();
}
