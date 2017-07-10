package com.hartron.eoffice.service.impl;

import com.hartron.eoffice.service.DesignationService;
import com.hartron.eoffice.domain.Designation;
import com.hartron.eoffice.repository.DesignationRepository;
import com.hartron.eoffice.repository.search.DesignationSearchRepository;
import com.hartron.eoffice.service.dto.DesignationDTO;
import com.hartron.eoffice.service.mapper.DesignationMapper;
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
 * Service Implementation for managing Designation.
 */
@Service
public class DesignationServiceImpl implements DesignationService{

    private final Logger log = LoggerFactory.getLogger(DesignationServiceImpl.class);
    
    private final DesignationRepository designationRepository;

    private final DesignationMapper designationMapper;

    private final DesignationSearchRepository designationSearchRepository;

    public DesignationServiceImpl(DesignationRepository designationRepository, DesignationMapper designationMapper, DesignationSearchRepository designationSearchRepository) {
        this.designationRepository = designationRepository;
        this.designationMapper = designationMapper;
        this.designationSearchRepository = designationSearchRepository;
    }

    /**
     * Save a designation.
     *
     * @param designationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DesignationDTO save(DesignationDTO designationDTO) {
        log.debug("Request to save Designation : {}", designationDTO);
        Designation designation = designationMapper.designationDTOToDesignation(designationDTO);
        designation = designationRepository.save(designation);
        DesignationDTO result = designationMapper.designationToDesignationDTO(designation);
        designationSearchRepository.save(designation);
        return result;
    }

    /**
     *  Get all the designations.
     *  
     *  @return the list of entities
     */
    @Override
    public List<DesignationDTO> findAll() {
        log.debug("Request to get all Designations");
        List<DesignationDTO> result = designationRepository.findAll().stream()
            .map(designationMapper::designationToDesignationDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one designation by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public DesignationDTO findOne(String id) {
        log.debug("Request to get Designation : {}", id);
        Designation designation = designationRepository.findOne(UUID.fromString(id));
        DesignationDTO designationDTO = designationMapper.designationToDesignationDTO(designation);
        return designationDTO;
    }

    /**
     *  Delete the  designation by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Designation : {}", id);
        designationRepository.delete(UUID.fromString(id));
        designationSearchRepository.delete(UUID.fromString(id));
    }

    /**
     * Search for the designation corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    public List<DesignationDTO> search(String query) {
        log.debug("Request to search Designations for query {}", query);
        return StreamSupport
            .stream(designationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(designationMapper::designationToDesignationDTO)
            .collect(Collectors.toList());
    }
}
