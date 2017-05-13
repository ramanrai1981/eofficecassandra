package com.hartron.eoffice.service.mapper;

import com.hartron.eoffice.domain.*;
import com.hartron.eoffice.service.dto.FileLogDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity FileLog and its DTO FileLogDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FileLogMapper {

    FileLogDTO fileLogToFileLogDTO(FileLog fileLog);

    List<FileLogDTO> fileLogsToFileLogDTOs(List<FileLog> fileLogs);

    FileLog fileLogDTOToFileLog(FileLogDTO fileLogDTO);

    List<FileLog> fileLogDTOsToFileLogs(List<FileLogDTO> fileLogDTOs);
}
