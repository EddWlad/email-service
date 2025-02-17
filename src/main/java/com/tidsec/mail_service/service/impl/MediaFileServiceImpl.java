package com.tidsec.mail_service.service.impl;

import com.tidsec.mail_service.entities.MediaFile;
import com.tidsec.mail_service.repositories.IGenericRepository;
import com.tidsec.mail_service.repositories.IMediaFileRepository;
import com.tidsec.mail_service.service.IMediaFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MediaFileServiceImpl extends GenericServiceImpl<MediaFile, Long> implements IMediaFileService {
    private final IMediaFileRepository mediaFileRepository;


    @Override
    protected IGenericRepository<MediaFile, Long> getRepo() {
        return mediaFileRepository;
    }
}
