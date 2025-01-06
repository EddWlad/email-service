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
    /*@Override
    public List<User> getAll() {
        return userRepository.findByStatusNot(0);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id)
                .filter(user -> user.getStatus() != 0)
                .or(() -> {
                    throw new ModelNotFoundException("ID NOT FOUND: " + id);
                });
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(Long id, User user) {
        User userDb = userRepository.findById(id).orElseThrow(()-> new ModelNotFoundException("ID NOT FOUND: " + id));
        if(user != null)
        {
            userDb.setName(user.getName());
            userDb.setLastName(user.getLastName());
            userDb.setEmail(user.getEmail());
            userDb.setDateCreate(user.getDateCreate());
            userDb.setPassword(user.getPassword());
            userDb.setIdentification(user.getIdentification());
            userDb.setStatus(user.getStatus());
            return userRepository.save(userDb);
        }
        else {
            return null;
        }

    }

    @Override
    public boolean delete(Long id) {
        User userDb = userRepository.findById(id)
                .filter(user -> user.getStatus() != 0)
                .orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND OR INACTIVE: " + id));
        userDb.setStatus(0);
        userRepository.save(userDb);
        return true;
    }

    @Override
    public Long count() {
        return userRepository.count();
    }*/
}
