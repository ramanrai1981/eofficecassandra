package com.hartron.eoffice.repository.search;

import com.hartron.eoffice.domain.FileMovement;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

/**
 * Spring Data Elasticsearch repository for the FileMovement entity.
 */
public interface FileMovementSearchRepository extends ElasticsearchRepository<FileMovement, UUID> {
}
