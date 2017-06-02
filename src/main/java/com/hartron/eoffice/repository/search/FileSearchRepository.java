package com.hartron.eoffice.repository.search;

import com.hartron.eoffice.domain.File;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

/**
 * Spring Data Elasticsearch repository for the File entity.
 */
public interface FileSearchRepository extends ElasticsearchRepository<File, UUID> {
}
