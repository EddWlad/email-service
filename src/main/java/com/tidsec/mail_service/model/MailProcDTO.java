package com.tidsec.mail_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailProcDTO {
    private Integer quantity;
    private String maildate;
}
