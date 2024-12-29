package com.tidsec.mail_service.model;

import com.tidsec.mail_service.entities.Mail;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttachmentsDTO {
    private Long id;
    private Mail mail;
    private String routeAttachment;
    private Integer status;
}
