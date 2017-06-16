package com.hartron.eoffice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hartron.eoffice.security.SecurityUtils;
import com.hartron.eoffice.service.EmployeeService;
import com.hartron.eoffice.service.dto.EmployeeDTO;
import com.hartron.eoffice.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Employee.
 */
@RestController
@RequestMapping("/api")
public class EmployeeUpdatedResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeUpdatedResource.class);

    private static final String ENTITY_NAME = "employee";

    private final EmployeeService employeeService;

    public EmployeeUpdatedResource(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * POST  /employees : Create a new employee.
     *
     * @param employeeDTO the employeeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new employeeDTO, or with status 400 (Bad Request) if the employee has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/employeesupdated")
    @Timed
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) throws URISyntaxException {
        log.debug("REST request to save Employee : {}", employeeDTO);
        if (employeeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new employee cannot already have an ID")).body(null);
        }
        employeeDTO.setCreatedby(SecurityUtils.getCurrentUserLogin());
        employeeDTO.setCreatedate(ZonedDateTime.now());
        EmployeeDTO result = employeeService.save(employeeDTO);
        return ResponseEntity.created(new URI("/api/employees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /employees : Updates an existing employee.
     *
     * @param employeeDTO the employeeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated employeeDTO,
     * or with status 400 (Bad Request) if the employeeDTO is not valid,
     * or with status 500 (Internal Server Error) if the employeeDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/employeesupdated")
    @Timed
    public ResponseEntity<EmployeeDTO> updateEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) throws URISyntaxException {
        log.debug("REST request to update Employee : {}", employeeDTO);
        if (employeeDTO.getId() == null) {
            return createEmployee(employeeDTO);
        }
        employeeDTO.setUpdatedate(ZonedDateTime.now());
        EmployeeDTO result = employeeService.save(employeeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, employeeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /employees : get all the employees.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of employees in body
     */
    @GetMapping("/employeesupdated")
    @Timed
    public List<EmployeeDTO> getAllEmployees() {
        log.debug("REST request to get all Employees");
        return employeeService.findAll();
    }

    /**
     * GET  /employees/:id : get the "id" employee.
     *
     * @param id the id of the employeeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the employeeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/employeesupdated/{id}")
    @Timed
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable String id) {
        log.debug("REST request to get Employee : {}", id);
        EmployeeDTO employeeDTO = employeeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(employeeDTO));
    }

    /**
     * DELETE  /employees/:id : delete the "id" employee.
     *
     * @param id the id of the employeeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/employeesupdated/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmployee(@PathVariable String id) {
        log.debug("REST request to delete Employee : {}", id);
        employeeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
