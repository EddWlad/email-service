package com.tidsec.mail_service.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PaymentAgreementDTO {
    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    private String description;
    private Integer status;
}
