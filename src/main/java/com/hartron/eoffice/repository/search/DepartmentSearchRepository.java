package com.hartron.eoffice.repository.search;

import com.hartron.eoffice.domain.Department;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

/**
 * Spring Data Elasticsearch repository for the Department entity.
 */
public interface DepartmentSearchRepository extends ElasticsearchRepository<Department, UUID> {
}
