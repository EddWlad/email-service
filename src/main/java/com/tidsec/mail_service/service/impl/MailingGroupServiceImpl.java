package com.tidsec.mail_service.service.impl;

import com.tidsec.mail_service.entities.MailingGroup;
import com.tidsec.mail_service.repositories.IMailingGroupRepository;
import com.tidsec.mail_service.service.IMailingGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class MailingGroupServiceImpl implements IMailingGroupService {

    @Autowired
    private IMailingGroupRepository mailingGroupRepository;

    @Override
    public List<MailingGroup> getAll() {
        return mailingGroupRepository.findByStatusNot(0) ;
    }

    @Override
    public Optional<MailingGroup> findById(Long id) {
        return mailingGroupRepository.findById(id);
    }

    @Override
    public MailingGroup saveMailingGroup(MailingGroup mailingGroup) {
        return mailingGroupRepository.save(mailingGroup);
    }

    @Override
    public MailingGroup updateMailingGroup(Long id, MailingGroup mailingGroup) {
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
    public boolean deleteMailingGroup(Long id) {
        MailingGroup mailingGroupDb = mailingGroupRepository.findById(id).orElse(null);
        if(mailingGroupDb != null) {
            mailingGroupDb.setStatus(0);
            mailingGroupRepository.save(mailingGroupDb);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Long countMailingGroup() {
        return mailingGroupRepository.count();
    }
}
