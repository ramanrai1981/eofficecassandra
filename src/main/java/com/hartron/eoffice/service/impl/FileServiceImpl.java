package com.hartron.eoffice.service.impl;

import com.hartron.eoffice.service.FileService;
import com.hartron.eoffice.domain.File;
import com.hartron.eoffice.repository.FileRepository;
import com.hartron.eoffice.service.dto.FileDTO;
import com.hartron.eoffice.service.mapper.FileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing File.
 */
@Service
public class FileServiceImpl implements FileService{

    private final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);
    
    private final FileRepository fileRepository;

    private final FileMapper fileMapper;

    public FileServiceImpl(FileRepository fileRepository, FileMapper fileMapper) {
        this.fileRepository = fileRepository;
        this.fileMapper = fileMapper;
    }

    /**
     * Save a file.
     *
     * @param fileDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FileDTO save(FileDTO fileDTO) {
        log.debug("Request to save File : {}", fileDTO);
        File file = fileMapper.fileDTOToFile(fileDTO);
        file = fileRepository.save(file);
        FileDTO result = fileMapper.fileToFileDTO(file);
        return result;
    }

    /**
     *  Get all the files.
     *  
     *  @return the list of entities
     */
    @Override
    public List<FileDTO> findAll() {
        log.debug("Request to get all Files");
        List<FileDTO> result = fileRepository.findAll().stream()
            .map(fileMapper::fileToFileDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one file by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public FileDTO findOne(String id) {
        log.debug("Request to get File : {}", id);
        File file = fileRepository.findOne(UUID.fromString(id));
        FileDTO fileDTO = fileMapper.fileToFileDTO(file);
        return fileDTO;
    }

    /**
     *  Delete the  file by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete File : {}", id);
        fileRepository.delete(UUID.fromString(id));
    }
}
