package com.hartron.eoffice.repository.search;

import com.hartron.eoffice.domain.Designation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

/**
 * Spring Data Elasticsearch repository for the Designation entity.
 */
public interface DesignationSearchRepository extends ElasticsearchRepository<Designation, UUID> {
}
