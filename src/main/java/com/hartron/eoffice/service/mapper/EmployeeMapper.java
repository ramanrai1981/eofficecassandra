package com.hartron.eoffice.service.mapper;

import com.hartron.eoffice.domain.*;
import com.hartron.eoffice.service.dto.EmployeeDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Employee and its DTO EmployeeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EmployeeMapper {

    EmployeeDTO employeeToEmployeeDTO(Employee employee);

    List<EmployeeDTO> employeesToEmployeeDTOs(List<Employee> employees);

    Employee employeeDTOToEmployee(EmployeeDTO employeeDTO);

    List<Employee> employeeDTOsToEmployees(List<EmployeeDTO> employeeDTOs);
}
