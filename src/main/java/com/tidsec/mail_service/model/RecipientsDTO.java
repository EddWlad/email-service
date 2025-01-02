package com.tidsec.mail_service.model;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RecipientsDTO {
    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private Integer status;
}
