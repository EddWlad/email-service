package com.tidsec.mail_service.service.impl;

import com.tidsec.mail_service.entities.Recipients;
import com.tidsec.mail_service.repositories.IRecipientsRepository;
import com.tidsec.mail_service.service.IRecipientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipientsServiceImpl implements IRecipientsService {

    @Autowired
    private IRecipientsRepository recipientsRepository;

    @Override
    public List<Recipients> getAll() {
        return recipientsRepository.findByStatusNot(0);
    }

    @Override
    public Optional<Recipients> findById(Long id) {
        return recipientsRepository.findById(id);
    }

    @Override
    public Recipients saveRecipients(Recipients recipients) {
        return recipientsRepository.save(recipients);
    }

    @Override
    public Recipients updateRecipients(Long id, Recipients recipients) {
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
    public boolean deleteRecipients(Long id) {
        Recipients recipientsDb = recipientsRepository.findById(id).orElse(null);
        if(recipientsDb != null){
            recipientsDb.setStatus(0);
            recipientsRepository.save(recipientsDb);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Long countRecipients() {
        return recipientsRepository.count();
    }
}
