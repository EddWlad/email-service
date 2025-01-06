package com.tidsec.mail_service.service.impl;

import com.tidsec.mail_service.entities.MailingGroup;
import com.tidsec.mail_service.entities.Project;
import com.tidsec.mail_service.exception.ModelNotFoundException;
import com.tidsec.mail_service.repositories.IGenericRepository;
import com.tidsec.mail_service.repositories.IMailingGroupRepository;
import com.tidsec.mail_service.service.IGenericService;
import com.tidsec.mail_service.service.IMailingGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class MailingGroupServiceImpl extends GenericServiceImpl<MailingGroup, Long> implements IMailingGroupService {

    private final IMailingGroupRepository mailingGroupRepository;

    @Override
    protected IGenericRepository<MailingGroup, Long> getRepo() {
        return mailingGroupRepository;
    }

    /*@Override
    public List<MailingGroup> getAll() {
        return mailingGroupRepository.findByStatusNot(0) ;
    }

    @Override
    public Optional<MailingGroup> findById(Long id) {
        return mailingGroupRepository.findById(id)
                .filter(mailingGroup -> mailingGroup.getStatus() != 0)
                .or(() -> {
                    throw new ModelNotFoundException("ID NOT FOUND: " + id);
                });
    }

    @Override
    public MailingGroup save(MailingGroup mailingGroup) {
        return mailingGroupRepository.save(mailingGroup);
    }

    @Override
    public MailingGroup update(Long id, MailingGroup mailingGroup) {
        MailingGroup mailingGroupDb = mailingGroupRepository.findById(id).orElse(null);
        if(mailingGroup != null){
            mailingGroupDb.setNameGroup(mailingGroup.getNameGroup());
            mailingGroupDb.setDescription(mailingGroup.getDescription());
            mailingGroupDb.setStatus(mailingGroup.getStatus());
            return mailingGroupRepository.save(mailingGroupDb);
        }else{
            return null;
        }
    }

    @Override
    public boolean delete(Long id) {
        MailingGroup mailingGroupDb = mailingGroupRepository.findById(id)
                .filter(mailingGroup -> mailingGroup.getStatus() != 0)
                .orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND OR INACTIVE: " + id));
        mailingGroupDb.setStatus(0);
        mailingGroupRepository.save(mailingGroupDb);
        return true;
    }

    @Override
    public Long count() {
        return mailingGroupRepository.count();
    }*/
}
