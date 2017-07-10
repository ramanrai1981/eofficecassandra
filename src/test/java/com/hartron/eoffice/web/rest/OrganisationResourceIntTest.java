package com.hartron.eoffice.web.rest;

import com.hartron.eoffice.AbstractCassandraTest;
import com.hartron.eoffice.EofficeApp;

import com.hartron.eoffice.domain.Organisation;
import com.hartron.eoffice.repository.OrganisationRepository;
import com.hartron.eoffice.service.OrganisationService;
import com.hartron.eoffice.repository.search.OrganisationSearchRepository;
import com.hartron.eoffice.service.dto.OrganisationDTO;
import com.hartron.eoffice.service.mapper.OrganisationMapper;
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
 * Test class for the OrganisationResource REST controller.
 *
 * @see OrganisationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EofficeApp.class)
public class OrganisationResourceIntTest extends AbstractCassandraTest {

    private static final String DEFAULT_ORGNAME = "AAAAAAAAAA";
    private static final String UPDATED_ORGNAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final ZonedDateTime DEFAULT_CREATEDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATEDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATEDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATEDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_OWNER = "AAAAAAAAAA";
    private static final String UPDATED_OWNER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_ESTABLISHMENTDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ESTABLISHMENTDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private OrganisationRepository organisationRepository;

    @Autowired
    private OrganisationMapper organisationMapper;

    @Autowired
    private OrganisationService organisationService;

    @Autowired
    private OrganisationSearchRepository organisationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restOrganisationMockMvc;

    private Organisation organisation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OrganisationResource organisationResource = new OrganisationResource(organisationService);
        this.restOrganisationMockMvc = MockMvcBuilders.standaloneSetup(organisationResource)
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
    public static Organisation createEntity() {
        Organisation organisation = new Organisation()
                .orgname(DEFAULT_ORGNAME)
                .address(DEFAULT_ADDRESS)
                .active(DEFAULT_ACTIVE)
                .createdate(DEFAULT_CREATEDATE)
                .updatedate(DEFAULT_UPDATEDATE)
                .owner(DEFAULT_OWNER)
                .establishmentdate(DEFAULT_ESTABLISHMENTDATE);
        return organisation;
    }

    @Before
    public void initTest() {
        organisationRepository.deleteAll();
        organisationSearchRepository.deleteAll();
        organisation = createEntity();
    }

    @Test
    public void createOrganisation() throws Exception {
        int databaseSizeBeforeCreate = organisationRepository.findAll().size();

        // Create the Organisation
        OrganisationDTO organisationDTO = organisationMapper.organisationToOrganisationDTO(organisation);

        restOrganisationMockMvc.perform(post("/api/organisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organisationDTO)))
            .andExpect(status().isCreated());

        // Validate the Organisation in the database
        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeCreate + 1);
        Organisation testOrganisation = organisationList.get(organisationList.size() - 1);
        assertThat(testOrganisation.getOrgname()).isEqualTo(DEFAULT_ORGNAME);
        assertThat(testOrganisation.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testOrganisation.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testOrganisation.getCreatedate()).isEqualTo(DEFAULT_CREATEDATE);
        assertThat(testOrganisation.getUpdatedate()).isEqualTo(DEFAULT_UPDATEDATE);
        assertThat(testOrganisation.getOwner()).isEqualTo(DEFAULT_OWNER);
        assertThat(testOrganisation.getEstablishmentdate()).isEqualTo(DEFAULT_ESTABLISHMENTDATE);

        // Validate the Organisation in Elasticsearch
        Organisation organisationEs = organisationSearchRepository.findOne(testOrganisation.getId());
        assertThat(organisationEs).isEqualToComparingFieldByField(testOrganisation);
    }

    @Test
    public void createOrganisationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = organisationRepository.findAll().size();

        // Create the Organisation with an existing ID
        Organisation existingOrganisation = new Organisation();
        existingOrganisation.setId(UUID.randomUUID());
        OrganisationDTO existingOrganisationDTO = organisationMapper.organisationToOrganisationDTO(existingOrganisation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganisationMockMvc.perform(post("/api/organisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingOrganisationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkOwnerIsRequired() throws Exception {
        int databaseSizeBeforeTest = organisationRepository.findAll().size();
        // set the field null
        organisation.setOwner(null);

        // Create the Organisation, which fails.
        OrganisationDTO organisationDTO = organisationMapper.organisationToOrganisationDTO(organisation);

        restOrganisationMockMvc.perform(post("/api/organisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organisationDTO)))
            .andExpect(status().isBadRequest());

        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllOrganisations() throws Exception {
        // Initialize the database
        organisationRepository.save(organisation);

        // Get all the organisationList
        restOrganisationMockMvc.perform(get("/api/organisations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organisation.getId().toString())))
            .andExpect(jsonPath("$.[*].orgname").value(hasItem(DEFAULT_ORGNAME.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdate").value(hasItem(sameInstant(DEFAULT_CREATEDATE))))
            .andExpect(jsonPath("$.[*].updatedate").value(hasItem(sameInstant(DEFAULT_UPDATEDATE))))
            .andExpect(jsonPath("$.[*].owner").value(hasItem(DEFAULT_OWNER.toString())))
            .andExpect(jsonPath("$.[*].establishmentdate").value(hasItem(sameInstant(DEFAULT_ESTABLISHMENTDATE))));
    }

    @Test
    public void getOrganisation() throws Exception {
        // Initialize the database
        organisationRepository.save(organisation);

        // Get the organisation
        restOrganisationMockMvc.perform(get("/api/organisations/{id}", organisation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(organisation.getId().toString()))
            .andExpect(jsonPath("$.orgname").value(DEFAULT_ORGNAME.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdate").value(sameInstant(DEFAULT_CREATEDATE)))
            .andExpect(jsonPath("$.updatedate").value(sameInstant(DEFAULT_UPDATEDATE)))
            .andExpect(jsonPath("$.owner").value(DEFAULT_OWNER.toString()))
            .andExpect(jsonPath("$.establishmentdate").value(sameInstant(DEFAULT_ESTABLISHMENTDATE)));
    }

    @Test
    public void getNonExistingOrganisation() throws Exception {
        // Get the organisation
        restOrganisationMockMvc.perform(get("/api/organisations/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateOrganisation() throws Exception {
        // Initialize the database
        organisationRepository.save(organisation);
        organisationSearchRepository.save(organisation);
        int databaseSizeBeforeUpdate = organisationRepository.findAll().size();

        // Update the organisation
        Organisation updatedOrganisation = organisationRepository.findOne(organisation.getId());
        updatedOrganisation
                .orgname(UPDATED_ORGNAME)
                .address(UPDATED_ADDRESS)
                .active(UPDATED_ACTIVE)
                .createdate(UPDATED_CREATEDATE)
                .updatedate(UPDATED_UPDATEDATE)
                .owner(UPDATED_OWNER)
                .establishmentdate(UPDATED_ESTABLISHMENTDATE);
        OrganisationDTO organisationDTO = organisationMapper.organisationToOrganisationDTO(updatedOrganisation);

        restOrganisationMockMvc.perform(put("/api/organisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organisationDTO)))
            .andExpect(status().isOk());

        // Validate the Organisation in the database
        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeUpdate);
        Organisation testOrganisation = organisationList.get(organisationList.size() - 1);
        assertThat(testOrganisation.getOrgname()).isEqualTo(UPDATED_ORGNAME);
        assertThat(testOrganisation.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testOrganisation.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testOrganisation.getCreatedate()).isEqualTo(UPDATED_CREATEDATE);
        assertThat(testOrganisation.getUpdatedate()).isEqualTo(UPDATED_UPDATEDATE);
        assertThat(testOrganisation.getOwner()).isEqualTo(UPDATED_OWNER);
        assertThat(testOrganisation.getEstablishmentdate()).isEqualTo(UPDATED_ESTABLISHMENTDATE);

        // Validate the Organisation in Elasticsearch
        Organisation organisationEs = organisationSearchRepository.findOne(testOrganisation.getId());
        assertThat(organisationEs).isEqualToComparingFieldByField(testOrganisation);
    }

    @Test
    public void updateNonExistingOrganisation() throws Exception {
        int databaseSizeBeforeUpdate = organisationRepository.findAll().size();

        // Create the Organisation
        OrganisationDTO organisationDTO = organisationMapper.organisationToOrganisationDTO(organisation);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOrganisationMockMvc.perform(put("/api/organisations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organisationDTO)))
            .andExpect(status().isCreated());

        // Validate the Organisation in the database
        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteOrganisation() throws Exception {
        // Initialize the database
        organisationRepository.save(organisation);
        organisationSearchRepository.save(organisation);
        int databaseSizeBeforeDelete = organisationRepository.findAll().size();

        // Get the organisation
        restOrganisationMockMvc.perform(delete("/api/organisations/{id}", organisation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean organisationExistsInEs = organisationSearchRepository.exists(organisation.getId());
        assertThat(organisationExistsInEs).isFalse();

        // Validate the database is empty
        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void searchOrganisation() throws Exception {
        // Initialize the database
        organisationRepository.save(organisation);
        organisationSearchRepository.save(organisation);

        // Search the organisation
        restOrganisationMockMvc.perform(get("/api/_search/organisations?query=id:" + organisation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organisation.getId().toString())))
            .andExpect(jsonPath("$.[*].orgname").value(hasItem(DEFAULT_ORGNAME.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdate").value(hasItem(sameInstant(DEFAULT_CREATEDATE))))
            .andExpect(jsonPath("$.[*].updatedate").value(hasItem(sameInstant(DEFAULT_UPDATEDATE))))
            .andExpect(jsonPath("$.[*].owner").value(hasItem(DEFAULT_OWNER.toString())))
            .andExpect(jsonPath("$.[*].establishmentdate").value(hasItem(sameInstant(DEFAULT_ESTABLISHMENTDATE))));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Organisation.class);
    }
}
