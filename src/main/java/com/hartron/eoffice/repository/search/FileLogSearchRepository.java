package com.hartron.eoffice.repository.search;

import com.hartron.eoffice.domain.FileLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

/**
 * Spring Data Elasticsearch repository for the FileLog entity.
 */
public interface FileLogSearchRepository extends ElasticsearchRepository<FileLog, UUID> {
}
