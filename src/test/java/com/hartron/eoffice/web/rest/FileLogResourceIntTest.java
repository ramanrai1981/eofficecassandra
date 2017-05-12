package com.hartron.eoffice.web.rest;

import com.hartron.eoffice.AbstractCassandraTest;
import com.hartron.eoffice.EofficeApp;

import com.hartron.eoffice.domain.FileLog;
import com.hartron.eoffice.repository.FileLogRepository;
import com.hartron.eoffice.service.FileLogService;
import com.hartron.eoffice.service.dto.FileLogDTO;
import com.hartron.eoffice.service.mapper.FileLogMapper;
import com.hartron.eoffice.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static com.hartron.eoffice.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FileLogResource REST controller.
 *
 * @see FileLogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EofficeApp.class)
public class FileLogResourceIntTest extends AbstractCassandraTest {

    private static final String DEFAULT_FILE_NO = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_MARK_FROM = "AAAAAAAAAA";
    private static final String UPDATED_MARK_FROM = "BBBBBBBBBB";

    private static final String DEFAULT_MARK_TO = "AAAAAAAAAA";
    private static final String UPDATED_MARK_TO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_MARK_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MARK_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    @Autowired
    private FileLogRepository fileLogRepository;

    @Autowired
    private FileLogMapper fileLogMapper;

    @Autowired
    private FileLogService fileLogService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restFileLogMockMvc;

    private FileLog fileLog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FileLogResource fileLogResource = new FileLogResource(fileLogService);
        this.restFileLogMockMvc = MockMvcBuilders.standaloneSetup(fileLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FileLog createEntity() {
        FileLog fileLog = new FileLog()
                .fileNo(DEFAULT_FILE_NO)
                .title(DEFAULT_TITLE)
                .markFrom(DEFAULT_MARK_FROM)
                .markTo(DEFAULT_MARK_TO)
                .markDate(DEFAULT_MARK_DATE)
                .comment(DEFAULT_COMMENT);
        return fileLog;
    }

    @Before
    public void initTest() {
        fileLogRepository.deleteAll();
        fileLog = createEntity();
    }

    @Test
    public void createFileLog() throws Exception {
        int databaseSizeBeforeCreate = fileLogRepository.findAll().size();

        // Create the FileLog
        FileLogDTO fileLogDTO = fileLogMapper.fileLogToFileLogDTO(fileLog);

        restFileLogMockMvc.perform(post("/api/file-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileLogDTO)))
            .andExpect(status().isCreated());

        // Validate the FileLog in the database
        List<FileLog> fileLogList = fileLogRepository.findAll();
        assertThat(fileLogList).hasSize(databaseSizeBeforeCreate + 1);
        FileLog testFileLog = fileLogList.get(fileLogList.size() - 1);
        assertThat(testFileLog.getFileNo()).isEqualTo(DEFAULT_FILE_NO);
        assertThat(testFileLog.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testFileLog.getMarkFrom()).isEqualTo(DEFAULT_MARK_FROM);
        assertThat(testFileLog.getMarkTo()).isEqualTo(DEFAULT_MARK_TO);
        assertThat(testFileLog.getMarkDate()).isEqualTo(DEFAULT_MARK_DATE);
        assertThat(testFileLog.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    public void createFileLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fileLogRepository.findAll().size();

        // Create the FileLog with an existing ID
        FileLog existingFileLog = new FileLog();
        existingFileLog.setId(UUID.randomUUID());
        FileLogDTO existingFileLogDTO = fileLogMapper.fileLogToFileLogDTO(existingFileLog);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFileLogMockMvc.perform(post("/api/file-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingFileLogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<FileLog> fileLogList = fileLogRepository.findAll();
        assertThat(fileLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllFileLogs() throws Exception {
        // Initialize the database
        fileLogRepository.save(fileLog);

        // Get all the fileLogList
        restFileLogMockMvc.perform(get("/api/file-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileLog.getId().toString())))
            .andExpect(jsonPath("$.[*].fileNo").value(hasItem(DEFAULT_FILE_NO.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].markFrom").value(hasItem(DEFAULT_MARK_FROM.toString())))
            .andExpect(jsonPath("$.[*].markTo").value(hasItem(DEFAULT_MARK_TO.toString())))
            .andExpect(jsonPath("$.[*].markDate").value(hasItem(sameInstant(DEFAULT_MARK_DATE))))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    public void getFileLog() throws Exception {
        // Initialize the database
        fileLogRepository.save(fileLog);

        // Get the fileLog
        restFileLogMockMvc.perform(get("/api/file-logs/{id}", fileLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fileLog.getId().toString()))
            .andExpect(jsonPath("$.fileNo").value(DEFAULT_FILE_NO.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.markFrom").value(DEFAULT_MARK_FROM.toString()))
            .andExpect(jsonPath("$.markTo").value(DEFAULT_MARK_TO.toString()))
            .andExpect(jsonPath("$.markDate").value(sameInstant(DEFAULT_MARK_DATE)))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    public void getNonExistingFileLog() throws Exception {
        // Get the fileLog
        restFileLogMockMvc.perform(get("/api/file-logs/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateFileLog() throws Exception {
        // Initialize the database
        fileLogRepository.save(fileLog);
        int databaseSizeBeforeUpdate = fileLogRepository.findAll().size();

        // Update the fileLog
        FileLog updatedFileLog = fileLogRepository.findOne(fileLog.getId());
        updatedFileLog
                .fileNo(UPDATED_FILE_NO)
                .title(UPDATED_TITLE)
                .markFrom(UPDATED_MARK_FROM)
                .markTo(UPDATED_MARK_TO)
                .markDate(UPDATED_MARK_DATE)
                .comment(UPDATED_COMMENT);
        FileLogDTO fileLogDTO = fileLogMapper.fileLogToFileLogDTO(updatedFileLog);

        restFileLogMockMvc.perform(put("/api/file-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileLogDTO)))
            .andExpect(status().isOk());

        // Validate the FileLog in the database
        List<FileLog> fileLogList = fileLogRepository.findAll();
        assertThat(fileLogList).hasSize(databaseSizeBeforeUpdate);
        FileLog testFileLog = fileLogList.get(fileLogList.size() - 1);
        assertThat(testFileLog.getFileNo()).isEqualTo(UPDATED_FILE_NO);
        assertThat(testFileLog.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testFileLog.getMarkFrom()).isEqualTo(UPDATED_MARK_FROM);
        assertThat(testFileLog.getMarkTo()).isEqualTo(UPDATED_MARK_TO);
        assertThat(testFileLog.getMarkDate()).isEqualTo(UPDATED_MARK_DATE);
        assertThat(testFileLog.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    public void updateNonExistingFileLog() throws Exception {
        int databaseSizeBeforeUpdate = fileLogRepository.findAll().size();

        // Create the FileLog
        FileLogDTO fileLogDTO = fileLogMapper.fileLogToFileLogDTO(fileLog);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFileLogMockMvc.perform(put("/api/file-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileLogDTO)))
            .andExpect(status().isCreated());

        // Validate the FileLog in the database
        List<FileLog> fileLogList = fileLogRepository.findAll();
        assertThat(fileLogList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteFileLog() throws Exception {
        // Initialize the database
        fileLogRepository.save(fileLog);
        int databaseSizeBeforeDelete = fileLogRepository.findAll().size();

        // Get the fileLog
        restFileLogMockMvc.perform(delete("/api/file-logs/{id}", fileLog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FileLog> fileLogList = fileLogRepository.findAll();
        assertThat(fileLogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileLog.class);
    }
}
