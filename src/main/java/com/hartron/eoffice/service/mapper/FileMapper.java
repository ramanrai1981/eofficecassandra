package com.hartron.eoffice.service.mapper;

import com.hartron.eoffice.domain.*;
import com.hartron.eoffice.service.dto.FileDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity File and its DTO FileDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FileMapper {

    FileDTO fileToFileDTO(File file);

    List<FileDTO> filesToFileDTOs(List<File> files);

    File fileDTOToFile(FileDTO fileDTO);

    List<File> fileDTOsToFiles(List<FileDTO> fileDTOs);
}
