package com.hartron.eoffice.service.impl;

import com.hartron.eoffice.service.OrganisationService;
import com.hartron.eoffice.domain.Organisation;
import com.hartron.eoffice.repository.OrganisationRepository;
import com.hartron.eoffice.service.dto.OrganisationDTO;
import com.hartron.eoffice.service.mapper.OrganisationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Organisation.
 */
@Service
public class OrganisationServiceImpl implements OrganisationService{

    private final Logger log = LoggerFactory.getLogger(OrganisationServiceImpl.class);

    private final OrganisationRepository organisationRepository;

    private final OrganisationMapper organisationMapper;

    public OrganisationServiceImpl(OrganisationRepository organisationRepository, OrganisationMapper organisationMapper) {
        this.organisationRepository = organisationRepository;
        this.organisationMapper = organisationMapper;
    }

    /**
     * Save a organisation.
     *
     * @param organisationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OrganisationDTO save(OrganisationDTO organisationDTO) {
        log.debug("Request to save Organisation : {}", organisationDTO);
        Organisation organisation = organisationMapper.organisationDTOToOrganisation(organisationDTO);
        organisation = organisationRepository.save(organisation);
        OrganisationDTO result = organisationMapper.organisationToOrganisationDTO(organisation);
        return result;
    }

    /**
     *  Get all the organisations.
     *
     *  @return the list of entities
     */
    @Override
    public List<OrganisationDTO> findAll() {
        log.debug("Request to get all Organisations");
        List<OrganisationDTO> result = organisationRepository.findAll().stream()
            .map(organisationMapper::organisationToOrganisationDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one organisation by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public OrganisationDTO findOne(String id) {
        log.debug("Request to get Organisation : {}", id);
        Organisation organisation = organisationRepository.findOne(UUID.fromString(id));
        OrganisationDTO organisationDTO = organisationMapper.organisationToOrganisationDTO(organisation);
        return organisationDTO;
    }

    /**
     *  Delete the  organisation by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Organisation : {}", id);
        organisationRepository.delete(UUID.fromString(id));
    }
    public List<OrganisationDTO> findAllByCreatedby(String createdby) {
        log.debug("Request to get all organisation by createdby");
        List<OrganisationDTO> result = organisationRepository.findAllByCreatedby(createdby).stream()
            .map(organisationMapper::organisationToOrganisationDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

}


