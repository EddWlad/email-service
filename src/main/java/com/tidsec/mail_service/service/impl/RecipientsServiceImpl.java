package com.tidsec.mail_service.service.impl;

import com.tidsec.mail_service.entities.Project;
import com.tidsec.mail_service.entities.Recipients;
import com.tidsec.mail_service.exception.ModelNotFoundException;
import com.tidsec.mail_service.repositories.IGenericRepository;
import com.tidsec.mail_service.repositories.IRecipientsRepository;
import com.tidsec.mail_service.service.IRecipientsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    @Override
    public Page<Recipients> listPage(Pageable pageable) {
        return recipientsRepository.findAll(pageable);
    }
}
