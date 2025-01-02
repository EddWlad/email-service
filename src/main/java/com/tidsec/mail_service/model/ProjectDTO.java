package com.tidsec.mail_service.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProjectDTO {
    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    private String company;
    private String description;
    private Integer status;
}
