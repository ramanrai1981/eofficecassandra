package com.hartron.eoffice.service;


import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Service Interface for File Management.
 */
public interface FileManagementService {

    void storeFile(MultipartFile file, String filename);

    Resource loadFile(String filename);
}
