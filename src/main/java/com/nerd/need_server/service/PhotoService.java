package com.nerd.need_server.service;

import com.nerd.need_server.exception.CustomException;
import com.nerd.need_server.handler.FileHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PhotoService {

    private final FileHandler fileHandler;

    public PhotoService(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    public String uploadPhoto(MultipartFile file) throws CustomException {
        try {
            return fileHandler.getImageUrl(file);
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
