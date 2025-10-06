package com.ecom.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    public String uploadImage(String path, MultipartFile file) throws IOException {
        // first get the name of file
        String originalFileName = file.getOriginalFilename();

        // we have to save it to server so need a unique id( it should be random)
        String randomId = UUID.randomUUID().toString();
        // now we should create a file name with this randomId
        String fileName = randomId.concat(originalFileName.substring(originalFileName.lastIndexOf('.')));
        String filePath = path + File.separator + fileName;

        File folder = new File(path);
        if(!folder.exists()){
            folder.mkdir();
        }

        // upload to server
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return fileName;
    }

}
