package com.example.smartpizza.service.impl;

import com.example.smartpizza.service.LoadAndUploadImgService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class LoadAndUploadImgServiceImpl implements LoadAndUploadImgService {

    @Value("${smartPizza.users.default.avatars.path}")
    private String defaultAvatar;
    @Override
    public String uploadImg(String path, MultipartFile multipartFile) throws IOException {
        String imageName = null;
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String uploadFileName = multipartFile.getOriginalFilename();
            imageName = System.nanoTime() + "_" + uploadFileName;

            Path fileNameAndPath = Paths.get(path, imageName);
            Files.write(fileNameAndPath, multipartFile.getBytes());
        }
        return imageName;
    }

    @Override
    public byte[] getBytes(String path, String avatar) throws IOException {
        File file = new File(path + avatar);
        FileInputStream fis;
        if (!avatar.equals("")) {
            fis = new FileInputStream(file);
            return IOUtils.toByteArray(fis);
        } else {
            file = new File(defaultAvatar);
            fis = new FileInputStream(file);
        }
        return IOUtils.toByteArray(fis);
    }

}
