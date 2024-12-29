package com.tidsec.mail_service.model;

import com.tidsec.mail_service.entities.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDTO {

    private Long id;
    private String name;
    private String description;
    private List<User> users = new ArrayList<User>();
    private Integer status;
}
