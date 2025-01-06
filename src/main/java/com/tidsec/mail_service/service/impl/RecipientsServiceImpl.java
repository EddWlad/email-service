package com.tidsec.mail_service.service.impl;

import com.tidsec.mail_service.entities.Project;
import com.tidsec.mail_service.entities.Recipients;
import com.tidsec.mail_service.exception.ModelNotFoundException;
import com.tidsec.mail_service.repositories.IGenericRepository;
import com.tidsec.mail_service.repositories.IRecipientsRepository;
import com.tidsec.mail_service.service.IRecipientsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipientsServiceImpl extends GenericServiceImpl<Recipients, Long> implements IRecipientsService {

    private final IRecipientsRepository recipientsRepository;

    @Override
    protected IGenericRepository<Recipients, Long> getRepo() {
        return recipientsRepository;
    }

    /*@Override
    public List<Recipients> getAll() {
        return recipientsRepository.findByStatusNot(0);
    }

    @Override
    public Optional<Recipients> findById(Long id) {
        return recipientsRepository.findById(id)
                .filter(recipients -> recipients.getStatus() != 0)
                .or(() -> {
                    throw new ModelNotFoundException("ID NOT FOUND: " + id);
                });
    }

    @Override
    public Recipients save(Recipients recipients) {
        return recipientsRepository.save(recipients);
    }

    @Override
    public Recipients update(Long id, Recipients recipients) {
        Recipients recipientsDb = recipientsRepository.findById(id).orElse(null);
        if(recipients != null){
            recipientsDb.setName(recipients.getName());
            recipientsDb.setEmail(recipients.getEmail());
            recipientsDb.setLastName(recipients.getLastName());
            recipientsDb.setPhone(recipients.getPhone());
            recipientsDb.setStatus(recipients.getStatus());
            return recipientsRepository.save(recipientsDb);
        }else{
            return null;
        }
    }

    @Override
    public boolean delete(Long id) {
        Recipients recipientsDb = recipientsRepository.findById(id)
                .filter(recipients -> recipients.getStatus() != 0)
                .orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND OR INACTIVE: " + id));
        recipientsDb.setStatus(0);
        recipientsRepository.save(recipientsDb);
        return true;
    }

    @Override
    public Long count() {
        return recipientsRepository.count();
    }*/
}
