package com.tidsec.mail_service.model;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MailingGroupDTO {
    @EqualsAndHashCode.Include
    private Long id;
    private String nameGroup;
    private String description;
    private Integer status;
    private List<Long> recipientIds;
}
