package com.hartron.eoffice.service;

import com.hartron.eoffice.service.dto.DesignationDTO;
import java.util.List;

/**
 * Service Interface for managing Designation.
 */
public interface DesignationService {

    /**
     * Save a designation.
     *
     * @param designationDTO the entity to save
     * @return the persisted entity
     */
    DesignationDTO save(DesignationDTO designationDTO);

    /**
     *  Get all the designations.
     *  
     *  @return the list of entities
     */
    List<DesignationDTO> findAll();

    /**
     *  Get the "id" designation.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DesignationDTO findOne(String id);

    /**
     *  Delete the "id" designation.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
