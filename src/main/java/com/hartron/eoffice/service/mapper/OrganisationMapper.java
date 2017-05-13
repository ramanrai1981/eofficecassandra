package com.hartron.eoffice.service.mapper;

import com.hartron.eoffice.domain.*;
import com.hartron.eoffice.service.dto.OrganisationDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Organisation and its DTO OrganisationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrganisationMapper {

    OrganisationDTO organisationToOrganisationDTO(Organisation organisation);

    List<OrganisationDTO> organisationsToOrganisationDTOs(List<Organisation> organisations);

    Organisation organisationDTOToOrganisation(OrganisationDTO organisationDTO);

    List<Organisation> organisationDTOsToOrganisations(List<OrganisationDTO> organisationDTOs);
}
