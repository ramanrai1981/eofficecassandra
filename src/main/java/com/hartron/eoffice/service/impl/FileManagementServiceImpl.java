package com.hartron.eoffice.service.impl;
import com.hartron.eoffice.service.FileManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * Service Implementation for managing Block.
 */
@Service
public class FileManagementServiceImpl implements FileManagementService {

    private final Logger log = LoggerFactory.getLogger(FileManagementServiceImpl.class);

    private final Path rootLocation = Paths.get("/var/uploads");

    public void storeFile(MultipartFile file, String filename){
        try {
            Files.copy(file.getInputStream(), this.rootLocation.resolve(filename));
        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }
    }

    public Resource loadFile(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }else{
                throw new RuntimeException("FAIL!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("FAIL!");
        }
    }


}
