package com.hartron.eoffice.repository.search;

import com.hartron.eoffice.domain.Employee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

/**
 * Spring Data Elasticsearch repository for the Employee entity.
 */
public interface EmployeeSearchRepository extends ElasticsearchRepository<Employee, UUID> {
}
