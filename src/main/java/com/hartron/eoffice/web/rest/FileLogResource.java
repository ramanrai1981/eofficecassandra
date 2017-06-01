package com.hartron.eoffice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hartron.eoffice.service.FileLogService;
import com.hartron.eoffice.web.rest.util.HeaderUtil;
import com.hartron.eoffice.service.dto.FileLogDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing FileLog.
 */
@RestController
@RequestMapping("/api")
public class FileLogResource {

    private final Logger log = LoggerFactory.getLogger(FileLogResource.class);

    private static final String ENTITY_NAME = "fileLog";
        
    private final FileLogService fileLogService;

    public FileLogResource(FileLogService fileLogService) {
        this.fileLogService = fileLogService;
    }

    /**
     * POST  /file-logs : Create a new fileLog.
     *
     * @param fileLogDTO the fileLogDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fileLogDTO, or with status 400 (Bad Request) if the fileLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/file-logs")
    @Timed
    public ResponseEntity<FileLogDTO> createFileLog(@RequestBody FileLogDTO fileLogDTO) throws URISyntaxException {
        log.debug("REST request to save FileLog : {}", fileLogDTO);
        if (fileLogDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new fileLog cannot already have an ID")).body(null);
        }
        FileLogDTO result = fileLogService.save(fileLogDTO);
        return ResponseEntity.created(new URI("/api/file-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /file-logs : Updates an existing fileLog.
     *
     * @param fileLogDTO the fileLogDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fileLogDTO,
     * or with status 400 (Bad Request) if the fileLogDTO is not valid,
     * or with status 500 (Internal Server Error) if the fileLogDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/file-logs")
    @Timed
    public ResponseEntity<FileLogDTO> updateFileLog(@RequestBody FileLogDTO fileLogDTO) throws URISyntaxException {
        log.debug("REST request to update FileLog : {}", fileLogDTO);
        if (fileLogDTO.getId() == null) {
            return createFileLog(fileLogDTO);
        }
        FileLogDTO result = fileLogService.save(fileLogDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fileLogDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /file-logs : get all the fileLogs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of fileLogs in body
     */
    @GetMapping("/file-logs")
    @Timed
    public List<FileLogDTO> getAllFileLogs() {
        log.debug("REST request to get all FileLogs");
        return fileLogService.findAll();
    }

    /**
     * GET  /file-logs/:id : get the "id" fileLog.
     *
     * @param id the id of the fileLogDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fileLogDTO, or with status 404 (Not Found)
     */
    @GetMapping("/file-logs/{id}")
    @Timed
    public ResponseEntity<FileLogDTO> getFileLog(@PathVariable String id) {
        log.debug("REST request to get FileLog : {}", id);
        FileLogDTO fileLogDTO = fileLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fileLogDTO));
    }

    /**
     * DELETE  /file-logs/:id : delete the "id" fileLog.
     *
     * @param id the id of the fileLogDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/file-logs/{id}")
    @Timed
    public ResponseEntity<Void> deleteFileLog(@PathVariable String id) {
        log.debug("REST request to delete FileLog : {}", id);
        fileLogService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/file-logs?query=:query : search for the fileLog corresponding
     * to the query.
     *
     * @param query the query of the fileLog search 
     * @return the result of the search
     */
    @GetMapping("/_search/file-logs")
    @Timed
    public List<FileLogDTO> searchFileLogs(@RequestParam String query) {
        log.debug("REST request to search FileLogs for query {}", query);
        return fileLogService.search(query);
    }


}
