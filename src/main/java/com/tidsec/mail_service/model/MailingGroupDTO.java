package com.tidsec.mail_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MailingGroupDTO {
    private Long id;
    private String nameGroup;
    private String description;
    private Integer status;
    private List<Long> recipientIds;
}
