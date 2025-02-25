package com.tidsec.mail_service.service.impl;


import com.tidsec.mail_service.entities.User;

import com.tidsec.mail_service.repositories.IGenericRepository;
import com.tidsec.mail_service.repositories.IUserRepository;
import com.tidsec.mail_service.service.IUserService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class UserServiceImpl extends GenericServiceImpl<User, Long> implements IUserService {

    private final IUserRepository userRepository;
    private final PasswordEncoder bcrypt;

    @Override
    protected IGenericRepository<User, Long> getRepo() {
        return userRepository;
    }

    @Override
    public User findOneByUsername(String username) {
        return userRepository.findOneByUsername(username);
    }

    @Override
    public void changePassword(String username, String newPassword) {
        userRepository.changePassword(username, bcrypt.encode(newPassword)) ;
    }


}
