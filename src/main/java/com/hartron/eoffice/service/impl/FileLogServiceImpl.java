package com.hartron.eoffice.service.impl;

import com.hartron.eoffice.service.FileLogService;
import com.hartron.eoffice.domain.FileLog;
import com.hartron.eoffice.repository.FileLogRepository;
import com.hartron.eoffice.repository.search.FileLogSearchRepository;
import com.hartron.eoffice.service.dto.FileLogDTO;
import com.hartron.eoffice.service.mapper.FileLogMapper;
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
 * Service Implementation for managing FileLog.
 */
@Service
public class FileLogServiceImpl implements FileLogService{

    private final Logger log = LoggerFactory.getLogger(FileLogServiceImpl.class);
    
    private final FileLogRepository fileLogRepository;

    private final FileLogMapper fileLogMapper;

    private final FileLogSearchRepository fileLogSearchRepository;

    public FileLogServiceImpl(FileLogRepository fileLogRepository, FileLogMapper fileLogMapper, FileLogSearchRepository fileLogSearchRepository) {
        this.fileLogRepository = fileLogRepository;
        this.fileLogMapper = fileLogMapper;
        this.fileLogSearchRepository = fileLogSearchRepository;
    }

    /**
     * Save a fileLog.
     *
     * @param fileLogDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FileLogDTO save(FileLogDTO fileLogDTO) {
        log.debug("Request to save FileLog : {}", fileLogDTO);
        FileLog fileLog = fileLogMapper.fileLogDTOToFileLog(fileLogDTO);
        fileLog = fileLogRepository.save(fileLog);
        FileLogDTO result = fileLogMapper.fileLogToFileLogDTO(fileLog);
        fileLogSearchRepository.save(fileLog);
        return result;
    }

    /**
     *  Get all the fileLogs.
     *  
     *  @return the list of entities
     */
    @Override
    public List<FileLogDTO> findAll() {
        log.debug("Request to get all FileLogs");
        List<FileLogDTO> result = fileLogRepository.findAll().stream()
            .map(fileLogMapper::fileLogToFileLogDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one fileLog by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public FileLogDTO findOne(String id) {
        log.debug("Request to get FileLog : {}", id);
        FileLog fileLog = fileLogRepository.findOne(UUID.fromString(id));
        FileLogDTO fileLogDTO = fileLogMapper.fileLogToFileLogDTO(fileLog);
        return fileLogDTO;
    }

    /**
     *  Delete the  fileLog by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete FileLog : {}", id);
        fileLogRepository.delete(UUID.fromString(id));
        fileLogSearchRepository.delete(UUID.fromString(id));
    }

    /**
     * Search for the fileLog corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    public List<FileLogDTO> search(String query) {
        log.debug("Request to search FileLogs for query {}", query);
        return StreamSupport
            .stream(fileLogSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(fileLogMapper::fileLogToFileLogDTO)
            .collect(Collectors.toList());
    }
}
