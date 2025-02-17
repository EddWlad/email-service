package com.tidsec.mail_service.model;

import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SignatureDTO {
    @EqualsAndHashCode.Include
    private Long id;
    private String photoUrl;
    private String name;
    private String lastName;
    private String positionCompany;
    private String phone;
    private String email;
    private String addressCompany;
    private Integer status = 1;
}
