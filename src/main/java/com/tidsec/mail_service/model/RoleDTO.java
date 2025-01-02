package com.tidsec.mail_service.model;

import com.tidsec.mail_service.entities.User;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RoleDTO {
    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    private String description;
    private List<User> users = new ArrayList<User>();
    private Integer status;
}
