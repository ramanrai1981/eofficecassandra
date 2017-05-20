package com.hartron.eoffice.web.rest;

import com.hartron.eoffice.AbstractCassandraTest;
import com.hartron.eoffice.EofficeApp;

import com.hartron.eoffice.domain.File;
import com.hartron.eoffice.repository.FileRepository;
import com.hartron.eoffice.service.FileService;
import com.hartron.eoffice.service.dto.FileDTO;
import com.hartron.eoffice.service.mapper.FileMapper;
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
 * Test class for the FileResource REST controller.
 *
 * @see FileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EofficeApp.class)
public class FileResourceIntTest extends AbstractCassandraTest {

    private static final String DEFAULT_FILE_NO = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_TAG = "AAAAAAAAAA";
    private static final String UPDATED_TAG = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_UPLOAD_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPLOAD_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private FileService fileService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restFileMockMvc;

    private File file;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FileResource fileResource = new FileResource(fileService);
        this.restFileMockMvc = MockMvcBuilders.standaloneSetup(fileResource)
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
    public static File createEntity() {
        File file = new File()
                .fileNo(DEFAULT_FILE_NO)
                .title(DEFAULT_TITLE)
                .tag(DEFAULT_TAG)
                .uploadDate(DEFAULT_UPLOAD_DATE)
                .status(DEFAULT_STATUS);
        return file;
    }

    @Before
    public void initTest() {
        fileRepository.deleteAll();
        file = createEntity();
    }

    @Test
    public void createFile() throws Exception {
        int databaseSizeBeforeCreate = fileRepository.findAll().size();

        // Create the File
        FileDTO fileDTO = fileMapper.fileToFileDTO(file);

        restFileMockMvc.perform(post("/api/files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileDTO)))
            .andExpect(status().isCreated());

        // Validate the File in the database
        List<File> fileList = fileRepository.findAll();
        assertThat(fileList).hasSize(databaseSizeBeforeCreate + 1);
        File testFile = fileList.get(fileList.size() - 1);
        assertThat(testFile.getFileNo()).isEqualTo(DEFAULT_FILE_NO);
        assertThat(testFile.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testFile.getTag()).isEqualTo(DEFAULT_TAG);
        assertThat(testFile.getUploadDate()).isEqualTo(DEFAULT_UPLOAD_DATE);
        assertThat(testFile.isStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    public void createFileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fileRepository.findAll().size();

        // Create the File with an existing ID
        File existingFile = new File();
        existingFile.setId(UUID.randomUUID());
        FileDTO existingFileDTO = fileMapper.fileToFileDTO(existingFile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFileMockMvc.perform(post("/api/files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<File> fileList = fileRepository.findAll();
        assertThat(fileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkFileNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = fileRepository.findAll().size();
        // set the field null
        file.setFileNo(null);

        // Create the File, which fails.
        FileDTO fileDTO = fileMapper.fileToFileDTO(file);

        restFileMockMvc.perform(post("/api/files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileDTO)))
            .andExpect(status().isBadRequest());

        List<File> fileList = fileRepository.findAll();
        assertThat(fileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = fileRepository.findAll().size();
        // set the field null
        file.setStatus(null);

        // Create the File, which fails.
        FileDTO fileDTO = fileMapper.fileToFileDTO(file);

        restFileMockMvc.perform(post("/api/files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileDTO)))
            .andExpect(status().isBadRequest());

        List<File> fileList = fileRepository.findAll();
        assertThat(fileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllFiles() throws Exception {
        // Initialize the database
        fileRepository.save(file);

        // Get all the fileList
        restFileMockMvc.perform(get("/api/files?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(file.getId().toString())))
            .andExpect(jsonPath("$.[*].fileNo").value(hasItem(DEFAULT_FILE_NO.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].tag").value(hasItem(DEFAULT_TAG.toString())))
            .andExpect(jsonPath("$.[*].uploadDate").value(hasItem(sameInstant(DEFAULT_UPLOAD_DATE))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    public void getFile() throws Exception {
        // Initialize the database
        fileRepository.save(file);

        // Get the file
        restFileMockMvc.perform(get("/api/files/{id}", file.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(file.getId().toString()))
            .andExpect(jsonPath("$.fileNo").value(DEFAULT_FILE_NO.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.tag").value(DEFAULT_TAG.toString()))
            .andExpect(jsonPath("$.uploadDate").value(sameInstant(DEFAULT_UPLOAD_DATE)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    public void getNonExistingFile() throws Exception {
        // Get the file
        restFileMockMvc.perform(get("/api/files/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateFile() throws Exception {
        // Initialize the database
        fileRepository.save(file);
        int databaseSizeBeforeUpdate = fileRepository.findAll().size();

        // Update the file
        File updatedFile = fileRepository.findOne(file.getId());
        updatedFile
                .fileNo(UPDATED_FILE_NO)
                .title(UPDATED_TITLE)
                .tag(UPDATED_TAG)
                .uploadDate(UPDATED_UPLOAD_DATE)
                .status(UPDATED_STATUS);
        FileDTO fileDTO = fileMapper.fileToFileDTO(updatedFile);

        restFileMockMvc.perform(put("/api/files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileDTO)))
            .andExpect(status().isOk());

        // Validate the File in the database
        List<File> fileList = fileRepository.findAll();
        assertThat(fileList).hasSize(databaseSizeBeforeUpdate);
        File testFile = fileList.get(fileList.size() - 1);
        assertThat(testFile.getFileNo()).isEqualTo(UPDATED_FILE_NO);
        assertThat(testFile.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testFile.getTag()).isEqualTo(UPDATED_TAG);
        assertThat(testFile.getUploadDate()).isEqualTo(UPDATED_UPLOAD_DATE);
        assertThat(testFile.isStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    public void updateNonExistingFile() throws Exception {
        int databaseSizeBeforeUpdate = fileRepository.findAll().size();

        // Create the File
        FileDTO fileDTO = fileMapper.fileToFileDTO(file);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFileMockMvc.perform(put("/api/files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileDTO)))
            .andExpect(status().isCreated());

        // Validate the File in the database
        List<File> fileList = fileRepository.findAll();
        assertThat(fileList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteFile() throws Exception {
        // Initialize the database
        fileRepository.save(file);
        int databaseSizeBeforeDelete = fileRepository.findAll().size();

        // Get the file
        restFileMockMvc.perform(delete("/api/files/{id}", file.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<File> fileList = fileRepository.findAll();
        assertThat(fileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(File.class);
    }
}
