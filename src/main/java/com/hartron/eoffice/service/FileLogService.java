package com.hartron.eoffice.service;

import com.hartron.eoffice.service.dto.FileLogDTO;
import java.util.List;

/**
 * Service Interface for managing FileLog.
 */
public interface FileLogService {

    /**
     * Save a fileLog.
     *
     * @param fileLogDTO the entity to save
     * @return the persisted entity
     */
    FileLogDTO save(FileLogDTO fileLogDTO);

    /**
     *  Get all the fileLogs.
     *  
     *  @return the list of entities
     */
    List<FileLogDTO> findAll();

    /**
     *  Get the "id" fileLog.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FileLogDTO findOne(String id);

    /**
     *  Delete the "id" fileLog.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
