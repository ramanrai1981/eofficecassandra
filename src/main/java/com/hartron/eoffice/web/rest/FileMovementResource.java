package com.hartron.eoffice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hartron.eoffice.service.FileMovementService;
import com.hartron.eoffice.web.rest.util.HeaderUtil;
import com.hartron.eoffice.service.dto.FileMovementDTO;
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
 * REST controller for managing FileMovement.
 */
@RestController
@RequestMapping("/api")
public class FileMovementResource {

    private final Logger log = LoggerFactory.getLogger(FileMovementResource.class);

    private static final String ENTITY_NAME = "fileMovement";
        
    private final FileMovementService fileMovementService;

    public FileMovementResource(FileMovementService fileMovementService) {
        this.fileMovementService = fileMovementService;
    }

    /**
     * POST  /file-movements : Create a new fileMovement.
     *
     * @param fileMovementDTO the fileMovementDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fileMovementDTO, or with status 400 (Bad Request) if the fileMovement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/file-movements")
    @Timed
    public ResponseEntity<FileMovementDTO> createFileMovement(@Valid @RequestBody FileMovementDTO fileMovementDTO) throws URISyntaxException {
        log.debug("REST request to save FileMovement : {}", fileMovementDTO);
        if (fileMovementDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new fileMovement cannot already have an ID")).body(null);
        }
        FileMovementDTO result = fileMovementService.save(fileMovementDTO);
        return ResponseEntity.created(new URI("/api/file-movements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /file-movements : Updates an existing fileMovement.
     *
     * @param fileMovementDTO the fileMovementDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fileMovementDTO,
     * or with status 400 (Bad Request) if the fileMovementDTO is not valid,
     * or with status 500 (Internal Server Error) if the fileMovementDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/file-movements")
    @Timed
    public ResponseEntity<FileMovementDTO> updateFileMovement(@Valid @RequestBody FileMovementDTO fileMovementDTO) throws URISyntaxException {
        log.debug("REST request to update FileMovement : {}", fileMovementDTO);
        if (fileMovementDTO.getId() == null) {
            return createFileMovement(fileMovementDTO);
        }
        FileMovementDTO result = fileMovementService.save(fileMovementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fileMovementDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /file-movements : get all the fileMovements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of fileMovements in body
     */
    @GetMapping("/file-movements")
    @Timed
    public List<FileMovementDTO> getAllFileMovements() {
        log.debug("REST request to get all FileMovements");
        return fileMovementService.findAll();
    }

    /**
     * GET  /file-movements/:id : get the "id" fileMovement.
     *
     * @param id the id of the fileMovementDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fileMovementDTO, or with status 404 (Not Found)
     */
    @GetMapping("/file-movements/{id}")
    @Timed
    public ResponseEntity<FileMovementDTO> getFileMovement(@PathVariable String id) {
        log.debug("REST request to get FileMovement : {}", id);
        FileMovementDTO fileMovementDTO = fileMovementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fileMovementDTO));
    }

    /**
     * DELETE  /file-movements/:id : delete the "id" fileMovement.
     *
     * @param id the id of the fileMovementDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/file-movements/{id}")
    @Timed
    public ResponseEntity<Void> deleteFileMovement(@PathVariable String id) {
        log.debug("REST request to delete FileMovement : {}", id);
        fileMovementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
