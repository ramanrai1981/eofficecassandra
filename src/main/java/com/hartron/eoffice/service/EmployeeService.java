package com.hartron.eoffice.service;

import com.hartron.eoffice.service.dto.EmployeeDTO;
import java.util.List;

/**
 * Service Interface for managing Employee.
 */
public interface EmployeeService {

    /**
     * Save a employee.
     *
     * @param employeeDTO the entity to save
     * @return the persisted entity
     */
    EmployeeDTO save(EmployeeDTO employeeDTO);

    /**
     *  Get all the employees.
     *  
     *  @return the list of entities
     */
    List<EmployeeDTO> findAll();

    /**
     *  Get the "id" employee.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    EmployeeDTO findOne(String id);

    /**
     *  Delete the "id" employee.
     *
     *  @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the employee corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<EmployeeDTO> search(String query);
}
