package com.hartron.eoffice.service.mapper;

import com.hartron.eoffice.domain.*;
import com.hartron.eoffice.service.dto.FileMovementDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity FileMovement and its DTO FileMovementDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FileMovementMapper {

    FileMovementDTO fileMovementToFileMovementDTO(FileMovement fileMovement);

    List<FileMovementDTO> fileMovementsToFileMovementDTOs(List<FileMovement> fileMovements);

    FileMovement fileMovementDTOToFileMovement(FileMovementDTO fileMovementDTO);

    List<FileMovement> fileMovementDTOsToFileMovements(List<FileMovementDTO> fileMovementDTOs);
}
