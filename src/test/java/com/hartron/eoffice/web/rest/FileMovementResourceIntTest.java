package com.hartron.eoffice.web.rest;

import com.hartron.eoffice.AbstractCassandraTest;
import com.hartron.eoffice.EofficeApp;

import com.hartron.eoffice.domain.FileMovement;
import com.hartron.eoffice.repository.FileMovementRepository;
import com.hartron.eoffice.service.FileMovementService;
import com.hartron.eoffice.repository.search.FileMovementSearchRepository;
import com.hartron.eoffice.service.dto.FileMovementDTO;
import com.hartron.eoffice.service.mapper.FileMovementMapper;
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
 * Test class for the FileMovementResource REST controller.
 *
 * @see FileMovementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EofficeApp.class)
public class FileMovementResourceIntTest extends AbstractCassandraTest {

    private static final UUID DEFAULT_FILE_ID = UUID.randomUUID();
    private static final UUID UPDATED_FILE_ID = UUID.randomUUID();

    private static final String DEFAULT_MARK_FROM = "AAAAAAAAAA";
    private static final String UPDATED_MARK_FROM = "BBBBBBBBBB";

    private static final String DEFAULT_MARK_TO = "AAAAAAAAAA";
    private static final String UPDATED_MARK_TO = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_MARK_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MARK_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_ACTION_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    @Autowired
    private FileMovementRepository fileMovementRepository;

    @Autowired
    private FileMovementMapper fileMovementMapper;

    @Autowired
    private FileMovementService fileMovementService;

    @Autowired
    private FileMovementSearchRepository fileMovementSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restFileMovementMockMvc;

    private FileMovement fileMovement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FileMovementResource fileMovementResource = new FileMovementResource(fileMovementService);
        this.restFileMovementMockMvc = MockMvcBuilders.standaloneSetup(fileMovementResource)
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
    public static FileMovement createEntity() {
        FileMovement fileMovement = new FileMovement()
                .fileId(DEFAULT_FILE_ID)
                .markFrom(DEFAULT_MARK_FROM)
                .markTo(DEFAULT_MARK_TO)
                .fileName(DEFAULT_FILE_NAME)
                .markDate(DEFAULT_MARK_DATE)
                .updateDate(DEFAULT_UPDATE_DATE)
                .actionStatus(DEFAULT_ACTION_STATUS)
                .comment(DEFAULT_COMMENT);
        return fileMovement;
    }

    @Before
    public void initTest() {
        fileMovementRepository.deleteAll();
        fileMovementSearchRepository.deleteAll();
        fileMovement = createEntity();
    }

    @Test
    public void createFileMovement() throws Exception {
        int databaseSizeBeforeCreate = fileMovementRepository.findAll().size();

        // Create the FileMovement
        FileMovementDTO fileMovementDTO = fileMovementMapper.fileMovementToFileMovementDTO(fileMovement);

        restFileMovementMockMvc.perform(post("/api/file-movements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileMovementDTO)))
            .andExpect(status().isCreated());

        // Validate the FileMovement in the database
        List<FileMovement> fileMovementList = fileMovementRepository.findAll();
        assertThat(fileMovementList).hasSize(databaseSizeBeforeCreate + 1);
        FileMovement testFileMovement = fileMovementList.get(fileMovementList.size() - 1);
        assertThat(testFileMovement.getFileId()).isEqualTo(DEFAULT_FILE_ID);
        assertThat(testFileMovement.getMarkFrom()).isEqualTo(DEFAULT_MARK_FROM);
        assertThat(testFileMovement.getMarkTo()).isEqualTo(DEFAULT_MARK_TO);
        assertThat(testFileMovement.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testFileMovement.getMarkDate()).isEqualTo(DEFAULT_MARK_DATE);
        assertThat(testFileMovement.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testFileMovement.getActionStatus()).isEqualTo(DEFAULT_ACTION_STATUS);
        assertThat(testFileMovement.getComment()).isEqualTo(DEFAULT_COMMENT);

        // Validate the FileMovement in Elasticsearch
        FileMovement fileMovementEs = fileMovementSearchRepository.findOne(testFileMovement.getId());
        assertThat(fileMovementEs).isEqualToComparingFieldByField(testFileMovement);
    }

    @Test
    public void createFileMovementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fileMovementRepository.findAll().size();

        // Create the FileMovement with an existing ID
        FileMovement existingFileMovement = new FileMovement();
        existingFileMovement.setId(UUID.randomUUID());
        FileMovementDTO existingFileMovementDTO = fileMovementMapper.fileMovementToFileMovementDTO(existingFileMovement);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFileMovementMockMvc.perform(post("/api/file-movements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingFileMovementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<FileMovement> fileMovementList = fileMovementRepository.findAll();
        assertThat(fileMovementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkFileIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = fileMovementRepository.findAll().size();
        // set the field null
        fileMovement.setFileId(null);

        // Create the FileMovement, which fails.
        FileMovementDTO fileMovementDTO = fileMovementMapper.fileMovementToFileMovementDTO(fileMovement);

        restFileMovementMockMvc.perform(post("/api/file-movements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileMovementDTO)))
            .andExpect(status().isBadRequest());

        List<FileMovement> fileMovementList = fileMovementRepository.findAll();
        assertThat(fileMovementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkMarkFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = fileMovementRepository.findAll().size();
        // set the field null
        fileMovement.setMarkFrom(null);

        // Create the FileMovement, which fails.
        FileMovementDTO fileMovementDTO = fileMovementMapper.fileMovementToFileMovementDTO(fileMovement);

        restFileMovementMockMvc.perform(post("/api/file-movements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileMovementDTO)))
            .andExpect(status().isBadRequest());

        List<FileMovement> fileMovementList = fileMovementRepository.findAll();
        assertThat(fileMovementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkMarkToIsRequired() throws Exception {
        int databaseSizeBeforeTest = fileMovementRepository.findAll().size();
        // set the field null
        fileMovement.setMarkTo(null);

        // Create the FileMovement, which fails.
        FileMovementDTO fileMovementDTO = fileMovementMapper.fileMovementToFileMovementDTO(fileMovement);

        restFileMovementMockMvc.perform(post("/api/file-movements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileMovementDTO)))
            .andExpect(status().isBadRequest());

        List<FileMovement> fileMovementList = fileMovementRepository.findAll();
        assertThat(fileMovementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkActionStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = fileMovementRepository.findAll().size();
        // set the field null
        fileMovement.setActionStatus(null);

        // Create the FileMovement, which fails.
        FileMovementDTO fileMovementDTO = fileMovementMapper.fileMovementToFileMovementDTO(fileMovement);

        restFileMovementMockMvc.perform(post("/api/file-movements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileMovementDTO)))
            .andExpect(status().isBadRequest());

        List<FileMovement> fileMovementList = fileMovementRepository.findAll();
        assertThat(fileMovementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllFileMovements() throws Exception {
        // Initialize the database
        fileMovementRepository.save(fileMovement);

        // Get all the fileMovementList
        restFileMovementMockMvc.perform(get("/api/file-movements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileMovement.getId().toString())))
            .andExpect(jsonPath("$.[*].fileId").value(hasItem(DEFAULT_FILE_ID.toString())))
            .andExpect(jsonPath("$.[*].markFrom").value(hasItem(DEFAULT_MARK_FROM.toString())))
            .andExpect(jsonPath("$.[*].markTo").value(hasItem(DEFAULT_MARK_TO.toString())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME.toString())))
            .andExpect(jsonPath("$.[*].markDate").value(hasItem(sameInstant(DEFAULT_MARK_DATE))))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE))))
            .andExpect(jsonPath("$.[*].actionStatus").value(hasItem(DEFAULT_ACTION_STATUS.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    public void getFileMovement() throws Exception {
        // Initialize the database
        fileMovementRepository.save(fileMovement);

        // Get the fileMovement
        restFileMovementMockMvc.perform(get("/api/file-movements/{id}", fileMovement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fileMovement.getId().toString()))
            .andExpect(jsonPath("$.fileId").value(DEFAULT_FILE_ID.toString()))
            .andExpect(jsonPath("$.markFrom").value(DEFAULT_MARK_FROM.toString()))
            .andExpect(jsonPath("$.markTo").value(DEFAULT_MARK_TO.toString()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME.toString()))
            .andExpect(jsonPath("$.markDate").value(sameInstant(DEFAULT_MARK_DATE)))
            .andExpect(jsonPath("$.updateDate").value(sameInstant(DEFAULT_UPDATE_DATE)))
            .andExpect(jsonPath("$.actionStatus").value(DEFAULT_ACTION_STATUS.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    public void getNonExistingFileMovement() throws Exception {
        // Get the fileMovement
        restFileMovementMockMvc.perform(get("/api/file-movements/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateFileMovement() throws Exception {
        // Initialize the database
        fileMovementRepository.save(fileMovement);
        fileMovementSearchRepository.save(fileMovement);
        int databaseSizeBeforeUpdate = fileMovementRepository.findAll().size();

        // Update the fileMovement
        FileMovement updatedFileMovement = fileMovementRepository.findOne(fileMovement.getId());
        updatedFileMovement
                .fileId(UPDATED_FILE_ID)
                .markFrom(UPDATED_MARK_FROM)
                .markTo(UPDATED_MARK_TO)
                .fileName(UPDATED_FILE_NAME)
                .markDate(UPDATED_MARK_DATE)
                .updateDate(UPDATED_UPDATE_DATE)
                .actionStatus(UPDATED_ACTION_STATUS)
                .comment(UPDATED_COMMENT);
        FileMovementDTO fileMovementDTO = fileMovementMapper.fileMovementToFileMovementDTO(updatedFileMovement);

        restFileMovementMockMvc.perform(put("/api/file-movements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileMovementDTO)))
            .andExpect(status().isOk());

        // Validate the FileMovement in the database
        List<FileMovement> fileMovementList = fileMovementRepository.findAll();
        assertThat(fileMovementList).hasSize(databaseSizeBeforeUpdate);
        FileMovement testFileMovement = fileMovementList.get(fileMovementList.size() - 1);
        assertThat(testFileMovement.getFileId()).isEqualTo(UPDATED_FILE_ID);
        assertThat(testFileMovement.getMarkFrom()).isEqualTo(UPDATED_MARK_FROM);
        assertThat(testFileMovement.getMarkTo()).isEqualTo(UPDATED_MARK_TO);
        assertThat(testFileMovement.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testFileMovement.getMarkDate()).isEqualTo(UPDATED_MARK_DATE);
        assertThat(testFileMovement.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testFileMovement.getActionStatus()).isEqualTo(UPDATED_ACTION_STATUS);
        assertThat(testFileMovement.getComment()).isEqualTo(UPDATED_COMMENT);

        // Validate the FileMovement in Elasticsearch
        FileMovement fileMovementEs = fileMovementSearchRepository.findOne(testFileMovement.getId());
        assertThat(fileMovementEs).isEqualToComparingFieldByField(testFileMovement);
    }

    @Test
    public void updateNonExistingFileMovement() throws Exception {
        int databaseSizeBeforeUpdate = fileMovementRepository.findAll().size();

        // Create the FileMovement
        FileMovementDTO fileMovementDTO = fileMovementMapper.fileMovementToFileMovementDTO(fileMovement);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFileMovementMockMvc.perform(put("/api/file-movements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileMovementDTO)))
            .andExpect(status().isCreated());

        // Validate the FileMovement in the database
        List<FileMovement> fileMovementList = fileMovementRepository.findAll();
        assertThat(fileMovementList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteFileMovement() throws Exception {
        // Initialize the database
        fileMovementRepository.save(fileMovement);
        fileMovementSearchRepository.save(fileMovement);
        int databaseSizeBeforeDelete = fileMovementRepository.findAll().size();

        // Get the fileMovement
        restFileMovementMockMvc.perform(delete("/api/file-movements/{id}", fileMovement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean fileMovementExistsInEs = fileMovementSearchRepository.exists(fileMovement.getId());
        assertThat(fileMovementExistsInEs).isFalse();

        // Validate the database is empty
        List<FileMovement> fileMovementList = fileMovementRepository.findAll();
        assertThat(fileMovementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void searchFileMovement() throws Exception {
        // Initialize the database
        fileMovementRepository.save(fileMovement);
        fileMovementSearchRepository.save(fileMovement);

        // Search the fileMovement
        restFileMovementMockMvc.perform(get("/api/_search/file-movements?query=id:" + fileMovement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileMovement.getId().toString())))
            .andExpect(jsonPath("$.[*].fileId").value(hasItem(DEFAULT_FILE_ID.toString())))
            .andExpect(jsonPath("$.[*].markFrom").value(hasItem(DEFAULT_MARK_FROM.toString())))
            .andExpect(jsonPath("$.[*].markTo").value(hasItem(DEFAULT_MARK_TO.toString())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME.toString())))
            .andExpect(jsonPath("$.[*].markDate").value(hasItem(sameInstant(DEFAULT_MARK_DATE))))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE))))
            .andExpect(jsonPath("$.[*].actionStatus").value(hasItem(DEFAULT_ACTION_STATUS.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileMovement.class);
    }
}
