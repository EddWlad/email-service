package com.tidsec.mail_service.model;

import com.tidsec.mail_service.entities.Mail;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AttachmentsDTO {
    @EqualsAndHashCode.Include
    private Long id;
    private Mail mail;
    private String routeAttachment;
    private Integer status;
}
