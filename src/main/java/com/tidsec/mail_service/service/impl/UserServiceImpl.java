package com.tidsec.mail_service.service.impl;

import com.tidsec.mail_service.entities.Project;
import com.tidsec.mail_service.entities.User;
import com.tidsec.mail_service.exception.ModelNotFoundException;
import com.tidsec.mail_service.repositories.IGenericRepository;
import com.tidsec.mail_service.repositories.IUserRepository;
import com.tidsec.mail_service.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends GenericServiceImpl<User, Long> implements IUserService {

    private final IUserRepository userRepository;

    @Override
    protected IGenericRepository<User, Long> getRepo() {
        return userRepository;
    }

}
