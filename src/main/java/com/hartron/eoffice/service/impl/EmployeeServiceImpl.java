package com.hartron.eoffice.service.impl;

import com.hartron.eoffice.service.EmployeeService;
import com.hartron.eoffice.domain.Employee;
import com.hartron.eoffice.repository.EmployeeRepository;
import com.hartron.eoffice.repository.search.EmployeeSearchRepository;
import com.hartron.eoffice.service.dto.EmployeeDTO;
import com.hartron.eoffice.service.mapper.EmployeeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Employee.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService{

    private final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    
    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    private final EmployeeSearchRepository employeeSearchRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, EmployeeSearchRepository employeeSearchRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.employeeSearchRepository = employeeSearchRepository;
    }

    /**
     * Save a employee.
     *
     * @param employeeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        log.debug("Request to save Employee : {}", employeeDTO);
        Employee employee = employeeMapper.employeeDTOToEmployee(employeeDTO);
        employee = employeeRepository.save(employee);
        EmployeeDTO result = employeeMapper.employeeToEmployeeDTO(employee);
        employeeSearchRepository.save(employee);
        return result;
    }

    /**
     *  Get all the employees.
     *  
     *  @return the list of entities
     */
    @Override
    public List<EmployeeDTO> findAll() {
        log.debug("Request to get all Employees");
        List<EmployeeDTO> result = employeeRepository.findAll().stream()
            .map(employeeMapper::employeeToEmployeeDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one employee by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public EmployeeDTO findOne(String id) {
        log.debug("Request to get Employee : {}", id);
        Employee employee = employeeRepository.findOne(UUID.fromString(id));
        EmployeeDTO employeeDTO = employeeMapper.employeeToEmployeeDTO(employee);
        return employeeDTO;
    }

    /**
     *  Delete the  employee by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Employee : {}", id);
        employeeRepository.delete(UUID.fromString(id));
        employeeSearchRepository.delete(UUID.fromString(id));
    }

    /**
     * Search for the employee corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    public List<EmployeeDTO> search(String query) {
        log.debug("Request to search Employees for query {}", query);
        return StreamSupport
            .stream(employeeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(employeeMapper::employeeToEmployeeDTO)
            .collect(Collectors.toList());
    }
}
