package com.hartron.eoffice.service.mapper;

import com.hartron.eoffice.domain.*;
import com.hartron.eoffice.service.dto.DesignationDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Designation and its DTO DesignationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DesignationMapper {

    DesignationDTO designationToDesignationDTO(Designation designation);

    List<DesignationDTO> designationsToDesignationDTOs(List<Designation> designations);

    Designation designationDTOToDesignation(DesignationDTO designationDTO);

    List<Designation> designationDTOsToDesignations(List<DesignationDTO> designationDTOs);
}
