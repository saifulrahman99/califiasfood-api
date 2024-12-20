package com.rahmandev.califiasfood.service;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
    String uploadFile(MultipartFile file, String folderName);

    void deleteImage(String publicId);
}
