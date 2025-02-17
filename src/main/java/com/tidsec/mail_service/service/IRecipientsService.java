package com.tidsec.mail_service.service;

import com.tidsec.mail_service.entities.Recipients;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRecipientsService extends IGenericService<Recipients, Long>{

    Page<Recipients> listPage(Pageable pageable);
}
