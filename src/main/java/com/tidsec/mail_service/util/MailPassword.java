package com.tidsec.mail_service.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailPassword {

    private String from;
    private String to;
    private String subject;
    private Map<String, Object> model;
}
