package com.tidsec.mail_service.service;

import org.springframework.web.multipart.MultipartFile;

public interface IFileStorageService {
    String saveFile(MultipartFile file);
}
