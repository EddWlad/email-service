package com.tidsec.mail_service.service;

import com.tidsec.mail_service.entities.User;

import java.util.List;
import java.util.Optional;

public interface IUserService extends IGenericService<User, Long>{
    User findOneByUsername(String username);
    void changePassword(String username, String newPassword);
}
