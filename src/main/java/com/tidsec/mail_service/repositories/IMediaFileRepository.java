package com.tidsec.mail_service.repositories;

import com.tidsec.mail_service.entities.MediaFile;
import org.springframework.stereotype.Repository;

@Repository
public interface IMediaFileRepository extends IGenericRepository<MediaFile, Long>{
}
