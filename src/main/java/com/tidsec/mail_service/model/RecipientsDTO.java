package com.tidsec.mail_service.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipientsDTO {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private Integer status;
}
