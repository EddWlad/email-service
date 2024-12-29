package com.tidsec.mail_service.model;

import com.tidsec.mail_service.entities.Role;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private Long id;
    private Date dateCreate = new Date();
    private String identification;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private Role role;
    private Integer status;
}
