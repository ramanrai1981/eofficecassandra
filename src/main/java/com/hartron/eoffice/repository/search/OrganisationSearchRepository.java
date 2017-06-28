package com.hartron.eoffice.repository.search;

import com.hartron.eoffice.domain.Organisation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

/**
 * Spring Data Elasticsearch repository for the Organisation entity.
 */
public interface OrganisationSearchRepository extends ElasticsearchRepository<Organisation, UUID> {
}
