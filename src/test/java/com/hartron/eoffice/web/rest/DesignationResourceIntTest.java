package com.hartron.eoffice.web.rest;

import com.hartron.eoffice.AbstractCassandraTest;
import com.hartron.eoffice.EofficeApp;

import com.hartron.eoffice.domain.Designation;
import com.hartron.eoffice.repository.DesignationRepository;
import com.hartron.eoffice.service.DesignationService;
import com.hartron.eoffice.repository.search.DesignationSearchRepository;
import com.hartron.eoffice.service.dto.DesignationDTO;
import com.hartron.eoffice.service.mapper.DesignationMapper;
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

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DesignationResource REST controller.
 *
 * @see DesignationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EofficeApp.class)
public class DesignationResourceIntTest extends AbstractCassandraTest {

    private static final String DEFAULT_ORGANISATIONID = "AAAAAAAAAA";
    private static final String UPDATED_ORGANISATIONID = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTMENTID = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTMENTID = "BBBBBBBBBB";

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    @Autowired
    private DesignationRepository designationRepository;

    @Autowired
    private DesignationMapper designationMapper;

    @Autowired
    private DesignationService designationService;

    @Autowired
    private DesignationSearchRepository designationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restDesignationMockMvc;

    private Designation designation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DesignationResource designationResource = new DesignationResource(designationService);
        this.restDesignationMockMvc = MockMvcBuilders.standaloneSetup(designationResource)
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
    public static Designation createEntity() {
        Designation designation = new Designation()
                .organisationid(DEFAULT_ORGANISATIONID)
                .departmentid(DEFAULT_DEPARTMENTID)
                .designation(DEFAULT_DESIGNATION);
        return designation;
    }

    @Before
    public void initTest() {
        designationRepository.deleteAll();
        designationSearchRepository.deleteAll();
        designation = createEntity();
    }

    @Test
    public void createDesignation() throws Exception {
        int databaseSizeBeforeCreate = designationRepository.findAll().size();

        // Create the Designation
        DesignationDTO designationDTO = designationMapper.designationToDesignationDTO(designation);

        restDesignationMockMvc.perform(post("/api/designations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designationDTO)))
            .andExpect(status().isCreated());

        // Validate the Designation in the database
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeCreate + 1);
        Designation testDesignation = designationList.get(designationList.size() - 1);
        assertThat(testDesignation.getOrganisationid()).isEqualTo(DEFAULT_ORGANISATIONID);
        assertThat(testDesignation.getDepartmentid()).isEqualTo(DEFAULT_DEPARTMENTID);
        assertThat(testDesignation.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);

        // Validate the Designation in Elasticsearch
        Designation designationEs = designationSearchRepository.findOne(testDesignation.getId());
        assertThat(designationEs).isEqualToComparingFieldByField(testDesignation);
    }

    @Test
    public void createDesignationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = designationRepository.findAll().size();

        // Create the Designation with an existing ID
        Designation existingDesignation = new Designation();
        existingDesignation.setId(UUID.randomUUID());
        DesignationDTO existingDesignationDTO = designationMapper.designationToDesignationDTO(existingDesignation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDesignationMockMvc.perform(post("/api/designations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingDesignationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkOrganisationidIsRequired() throws Exception {
        int databaseSizeBeforeTest = designationRepository.findAll().size();
        // set the field null
        designation.setOrganisationid(null);

        // Create the Designation, which fails.
        DesignationDTO designationDTO = designationMapper.designationToDesignationDTO(designation);

        restDesignationMockMvc.perform(post("/api/designations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designationDTO)))
            .andExpect(status().isBadRequest());

        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDepartmentidIsRequired() throws Exception {
        int databaseSizeBeforeTest = designationRepository.findAll().size();
        // set the field null
        designation.setDepartmentid(null);

        // Create the Designation, which fails.
        DesignationDTO designationDTO = designationMapper.designationToDesignationDTO(designation);

        restDesignationMockMvc.perform(post("/api/designations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designationDTO)))
            .andExpect(status().isBadRequest());

        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = designationRepository.findAll().size();
        // set the field null
        designation.setDesignation(null);

        // Create the Designation, which fails.
        DesignationDTO designationDTO = designationMapper.designationToDesignationDTO(designation);

        restDesignationMockMvc.perform(post("/api/designations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designationDTO)))
            .andExpect(status().isBadRequest());

        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllDesignations() throws Exception {
        // Initialize the database
        designationRepository.save(designation);

        // Get all the designationList
        restDesignationMockMvc.perform(get("/api/designations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designation.getId().toString())))
            .andExpect(jsonPath("$.[*].organisationid").value(hasItem(DEFAULT_ORGANISATIONID.toString())))
            .andExpect(jsonPath("$.[*].departmentid").value(hasItem(DEFAULT_DEPARTMENTID.toString())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())));
    }

    @Test
    public void getDesignation() throws Exception {
        // Initialize the database
        designationRepository.save(designation);

        // Get the designation
        restDesignationMockMvc.perform(get("/api/designations/{id}", designation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(designation.getId().toString()))
            .andExpect(jsonPath("$.organisationid").value(DEFAULT_ORGANISATIONID.toString()))
            .andExpect(jsonPath("$.departmentid").value(DEFAULT_DEPARTMENTID.toString()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION.toString()));
    }

    @Test
    public void getNonExistingDesignation() throws Exception {
        // Get the designation
        restDesignationMockMvc.perform(get("/api/designations/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateDesignation() throws Exception {
        // Initialize the database
        designationRepository.save(designation);
        designationSearchRepository.save(designation);
        int databaseSizeBeforeUpdate = designationRepository.findAll().size();

        // Update the designation
        Designation updatedDesignation = designationRepository.findOne(designation.getId());
        updatedDesignation
                .organisationid(UPDATED_ORGANISATIONID)
                .departmentid(UPDATED_DEPARTMENTID)
                .designation(UPDATED_DESIGNATION);
        DesignationDTO designationDTO = designationMapper.designationToDesignationDTO(updatedDesignation);

        restDesignationMockMvc.perform(put("/api/designations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designationDTO)))
            .andExpect(status().isOk());

        // Validate the Designation in the database
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeUpdate);
        Designation testDesignation = designationList.get(designationList.size() - 1);
        assertThat(testDesignation.getOrganisationid()).isEqualTo(UPDATED_ORGANISATIONID);
        assertThat(testDesignation.getDepartmentid()).isEqualTo(UPDATED_DEPARTMENTID);
        assertThat(testDesignation.getDesignation()).isEqualTo(UPDATED_DESIGNATION);

        // Validate the Designation in Elasticsearch
        Designation designationEs = designationSearchRepository.findOne(testDesignation.getId());
        assertThat(designationEs).isEqualToComparingFieldByField(testDesignation);
    }

    @Test
    public void updateNonExistingDesignation() throws Exception {
        int databaseSizeBeforeUpdate = designationRepository.findAll().size();

        // Create the Designation
        DesignationDTO designationDTO = designationMapper.designationToDesignationDTO(designation);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDesignationMockMvc.perform(put("/api/designations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designationDTO)))
            .andExpect(status().isCreated());

        // Validate the Designation in the database
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteDesignation() throws Exception {
        // Initialize the database
        designationRepository.save(designation);
        designationSearchRepository.save(designation);
        int databaseSizeBeforeDelete = designationRepository.findAll().size();

        // Get the designation
        restDesignationMockMvc.perform(delete("/api/designations/{id}", designation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean designationExistsInEs = designationSearchRepository.exists(designation.getId());
        assertThat(designationExistsInEs).isFalse();

        // Validate the database is empty
        List<Designation> designationList = designationRepository.findAll();
        assertThat(designationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void searchDesignation() throws Exception {
        // Initialize the database
        designationRepository.save(designation);
        designationSearchRepository.save(designation);

        // Search the designation
        restDesignationMockMvc.perform(get("/api/_search/designations?query=id:" + designation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designation.getId().toString())))
            .andExpect(jsonPath("$.[*].organisationid").value(hasItem(DEFAULT_ORGANISATIONID.toString())))
            .andExpect(jsonPath("$.[*].departmentid").value(hasItem(DEFAULT_DEPARTMENTID.toString())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Designation.class);
    }
}
