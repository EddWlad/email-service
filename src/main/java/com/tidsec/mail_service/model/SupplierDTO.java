package com.tidsec.mail_service.model;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SupplierDTO {
    @EqualsAndHashCode.Include
    private Long id;
    private String ruc;
    private String name;
    private String email;
    private Integer status;
}
