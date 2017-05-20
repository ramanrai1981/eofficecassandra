package com.hartron.eoffice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hartron.eoffice.service.OrganisationService;
import com.hartron.eoffice.web.rest.util.HeaderUtil;
import com.hartron.eoffice.service.dto.OrganisationDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * REST controller for managing Organisation.
 */
@RestController
@RequestMapping("/api")
public class OrganisationResource {

    private final Logger log = LoggerFactory.getLogger(OrganisationResource.class);

    private static final String ENTITY_NAME = "organisation";
        
    private final OrganisationService organisationService;

    public OrganisationResource(OrganisationService organisationService) {
        this.organisationService = organisationService;
    }

    /**
     * POST  /organisations : Create a new organisation.
     *
     * @param organisationDTO the organisationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new organisationDTO, or with status 400 (Bad Request) if the organisation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/organisations")
    @Timed
    public ResponseEntity<OrganisationDTO> createOrganisation(@Valid @RequestBody OrganisationDTO organisationDTO) throws URISyntaxException {
        log.debug("REST request to save Organisation : {}", organisationDTO);
        if (organisationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new organisation cannot already have an ID")).body(null);
        }
        OrganisationDTO result = organisationService.save(organisationDTO);
        return ResponseEntity.created(new URI("/api/organisations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /organisations : Updates an existing organisation.
     *
     * @param organisationDTO the organisationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated organisationDTO,
     * or with status 400 (Bad Request) if the organisationDTO is not valid,
     * or with status 500 (Internal Server Error) if the organisationDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/organisations")
    @Timed
    public ResponseEntity<OrganisationDTO> updateOrganisation(@Valid @RequestBody OrganisationDTO organisationDTO) throws URISyntaxException {
        log.debug("REST request to update Organisation : {}", organisationDTO);
        if (organisationDTO.getId() == null) {
            return createOrganisation(organisationDTO);
        }
        OrganisationDTO result = organisationService.save(organisationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, organisationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /organisations : get all the organisations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of organisations in body
     */
    @GetMapping("/organisations")
    @Timed
    public List<OrganisationDTO> getAllOrganisations() {
        log.debug("REST request to get all Organisations");
        return organisationService.findAll();
    }

    /**
     * GET  /organisations/:id : get the "id" organisation.
     *
     * @param id the id of the organisationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the organisationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/organisations/{id}")
    @Timed
    public ResponseEntity<OrganisationDTO> getOrganisation(@PathVariable String id) {
        log.debug("REST request to get Organisation : {}", id);
        OrganisationDTO organisationDTO = organisationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(organisationDTO));
    }

    /**
     * DELETE  /organisations/:id : delete the "id" organisation.
     *
     * @param id the id of the organisationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/organisations/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrganisation(@PathVariable String id) {
        log.debug("REST request to delete Organisation : {}", id);
        organisationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
