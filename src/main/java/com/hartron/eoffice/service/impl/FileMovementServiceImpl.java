package com.hartron.eoffice.service.impl;

import com.hartron.eoffice.service.FileMovementService;
import com.hartron.eoffice.domain.FileMovement;
import com.hartron.eoffice.repository.FileMovementRepository;
import com.hartron.eoffice.service.dto.FileMovementDTO;
import com.hartron.eoffice.service.mapper.FileMovementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing FileMovement.
 */
@Service
public class FileMovementServiceImpl implements FileMovementService{

    private final Logger log = LoggerFactory.getLogger(FileMovementServiceImpl.class);
    
    private final FileMovementRepository fileMovementRepository;

    private final FileMovementMapper fileMovementMapper;

    public FileMovementServiceImpl(FileMovementRepository fileMovementRepository, FileMovementMapper fileMovementMapper) {
        this.fileMovementRepository = fileMovementRepository;
        this.fileMovementMapper = fileMovementMapper;
    }

    /**
     * Save a fileMovement.
     *
     * @param fileMovementDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FileMovementDTO save(FileMovementDTO fileMovementDTO) {
        log.debug("Request to save FileMovement : {}", fileMovementDTO);
        FileMovement fileMovement = fileMovementMapper.fileMovementDTOToFileMovement(fileMovementDTO);
        fileMovement = fileMovementRepository.save(fileMovement);
        FileMovementDTO result = fileMovementMapper.fileMovementToFileMovementDTO(fileMovement);
        return result;
    }

    /**
     *  Get all the fileMovements.
     *  
     *  @return the list of entities
     */
    @Override
    public List<FileMovementDTO> findAll() {
        log.debug("Request to get all FileMovements");
        List<FileMovementDTO> result = fileMovementRepository.findAll().stream()
            .map(fileMovementMapper::fileMovementToFileMovementDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one fileMovement by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public FileMovementDTO findOne(String id) {
        log.debug("Request to get FileMovement : {}", id);
        FileMovement fileMovement = fileMovementRepository.findOne(UUID.fromString(id));
        FileMovementDTO fileMovementDTO = fileMovementMapper.fileMovementToFileMovementDTO(fileMovement);
        return fileMovementDTO;
    }

    /**
     *  Delete the  fileMovement by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete FileMovement : {}", id);
        fileMovementRepository.delete(UUID.fromString(id));
    }
}
