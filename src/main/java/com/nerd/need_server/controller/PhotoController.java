package com.nerd.need_server.controller;

import com.nerd.need_server.exception.CustomException;
import com.nerd.need_server.response.BaseResponse;
import com.nerd.need_server.service.PhotoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/photo")
public class PhotoController {

    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping("/")
    public BaseResponse<String> upload(@RequestParam MultipartFile file) throws CustomException {
        return new BaseResponse<>("사진을 업로드 했어요!", HttpStatus.OK.value(), photoService.uploadPhoto(file));
    }

    @GetMapping(value = "/{url}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE})
    public @ResponseBody
    byte[] getImage(@PathVariable String url) throws IOException {
        String absolutePath = new File("").getAbsolutePath() + "/images/";
        File file = new File(absolutePath + url);
        InputStream in = new FileInputStream(file);
        return in.readAllBytes();
    }


}
