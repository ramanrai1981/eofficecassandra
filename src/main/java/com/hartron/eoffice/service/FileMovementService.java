package com.hartron.eoffice.service;

import com.hartron.eoffice.service.dto.FileMovementDTO;
import java.util.List;

/**
 * Service Interface for managing FileMovement.
 */
public interface FileMovementService {

    /**
     * Save a fileMovement.
     *
     * @param fileMovementDTO the entity to save
     * @return the persisted entity
     */
    FileMovementDTO save(FileMovementDTO fileMovementDTO);

    /**
     *  Get all the fileMovements.
     *  
     *  @return the list of entities
     */
    List<FileMovementDTO> findAll();

    /**
     *  Get the "id" fileMovement.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FileMovementDTO findOne(String id);

    /**
     *  Delete the "id" fileMovement.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
