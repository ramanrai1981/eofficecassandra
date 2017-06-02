package com.hartron.eoffice.web.rest;

import com.hartron.eoffice.AbstractCassandraTest;
import com.hartron.eoffice.EofficeApp;

import com.hartron.eoffice.domain.Employee;
import com.hartron.eoffice.repository.EmployeeRepository;
import com.hartron.eoffice.service.EmployeeService;
import com.hartron.eoffice.repository.search.EmployeeSearchRepository;
import com.hartron.eoffice.service.dto.EmployeeDTO;
import com.hartron.eoffice.service.mapper.EmployeeMapper;
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
 * Test class for the EmployeeResource REST controller.
 *
 * @see EmployeeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EofficeApp.class)
public class EmployeeResourceIntTest extends AbstractCassandraTest {

    private static final String DEFAULT_EMPID = "AAAAAAAAAA";
    private static final String UPDATED_EMPID = "BBBBBBBBBB";

    private static final String DEFAULT_EMPNAME = "AAAAAAAAAA";
    private static final String UPDATED_EMPNAME = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTMENT = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTMENT = "BBBBBBBBBB";

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final String DEFAULT_EMAILID = "AAAAAAAAAA";
    private static final String UPDATED_EMAILID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATEOFBIRTH = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATEOFBIRTH = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATEOFJOINING = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATEOFJOINING = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_RELIEVINGDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_RELIEVINGDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final ZonedDateTime DEFAULT_CREATEDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATEDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATEDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATEDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_MOBILENUMBER = "AAAAAAAAAA";
    private static final String UPDATED_MOBILENUMBER = "BBBBBBBBBB";

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeSearchRepository employeeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restEmployeeMockMvc;

    private Employee employee;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EmployeeResource employeeResource = new EmployeeResource(employeeService);
        this.restEmployeeMockMvc = MockMvcBuilders.standaloneSetup(employeeResource)
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
    public static Employee createEntity() {
        Employee employee = new Employee()
                .empid(DEFAULT_EMPID)
                .empname(DEFAULT_EMPNAME)
                .department(DEFAULT_DEPARTMENT)
                .designation(DEFAULT_DESIGNATION)
                .emailid(DEFAULT_EMAILID)
                .dateofbirth(DEFAULT_DATEOFBIRTH)
                .dateofjoining(DEFAULT_DATEOFJOINING)
                .relievingdate(DEFAULT_RELIEVINGDATE)
                .active(DEFAULT_ACTIVE)
                .createdate(DEFAULT_CREATEDATE)
                .updatedate(DEFAULT_UPDATEDATE)
                .mobilenumber(DEFAULT_MOBILENUMBER);
        return employee;
    }

    @Before
    public void initTest() {
        employeeRepository.deleteAll();
        employeeSearchRepository.deleteAll();
        employee = createEntity();
    }

    @Test
    public void createEmployee() throws Exception {
        int databaseSizeBeforeCreate = employeeRepository.findAll().size();

        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.employeeToEmployeeDTO(employee);

        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isCreated());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate + 1);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getEmpid()).isEqualTo(DEFAULT_EMPID);
        assertThat(testEmployee.getEmpname()).isEqualTo(DEFAULT_EMPNAME);
        assertThat(testEmployee.getDepartment()).isEqualTo(DEFAULT_DEPARTMENT);
        assertThat(testEmployee.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testEmployee.getEmailid()).isEqualTo(DEFAULT_EMAILID);
        assertThat(testEmployee.getDateofbirth()).isEqualTo(DEFAULT_DATEOFBIRTH);
        assertThat(testEmployee.getDateofjoining()).isEqualTo(DEFAULT_DATEOFJOINING);
        assertThat(testEmployee.getRelievingdate()).isEqualTo(DEFAULT_RELIEVINGDATE);
        assertThat(testEmployee.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testEmployee.getCreatedate()).isEqualTo(DEFAULT_CREATEDATE);
        assertThat(testEmployee.getUpdatedate()).isEqualTo(DEFAULT_UPDATEDATE);
        assertThat(testEmployee.getMobilenumber()).isEqualTo(DEFAULT_MOBILENUMBER);

        // Validate the Employee in Elasticsearch
        Employee employeeEs = employeeSearchRepository.findOne(testEmployee.getId());
        assertThat(employeeEs).isEqualToComparingFieldByField(testEmployee);
    }

    @Test
    public void createEmployeeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeRepository.findAll().size();

        // Create the Employee with an existing ID
        Employee existingEmployee = new Employee();
        existingEmployee.setId(UUID.randomUUID());
        EmployeeDTO existingEmployeeDTO = employeeMapper.employeeToEmployeeDTO(existingEmployee);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingEmployeeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkEmpidIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setEmpid(null);

        // Create the Employee, which fails.
        EmployeeDTO employeeDTO = employeeMapper.employeeToEmployeeDTO(employee);

        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkMobilenumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setMobilenumber(null);

        // Create the Employee, which fails.
        EmployeeDTO employeeDTO = employeeMapper.employeeToEmployeeDTO(employee);

        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllEmployees() throws Exception {
        // Initialize the database
        employeeRepository.save(employee);

        // Get all the employeeList
        restEmployeeMockMvc.perform(get("/api/employees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().toString())))
            .andExpect(jsonPath("$.[*].empid").value(hasItem(DEFAULT_EMPID.toString())))
            .andExpect(jsonPath("$.[*].empname").value(hasItem(DEFAULT_EMPNAME.toString())))
            .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT.toString())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())))
            .andExpect(jsonPath("$.[*].emailid").value(hasItem(DEFAULT_EMAILID.toString())))
            .andExpect(jsonPath("$.[*].dateofbirth").value(hasItem(sameInstant(DEFAULT_DATEOFBIRTH))))
            .andExpect(jsonPath("$.[*].dateofjoining").value(hasItem(sameInstant(DEFAULT_DATEOFJOINING))))
            .andExpect(jsonPath("$.[*].relievingdate").value(hasItem(sameInstant(DEFAULT_RELIEVINGDATE))))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdate").value(hasItem(sameInstant(DEFAULT_CREATEDATE))))
            .andExpect(jsonPath("$.[*].updatedate").value(hasItem(sameInstant(DEFAULT_UPDATEDATE))))
            .andExpect(jsonPath("$.[*].mobilenumber").value(hasItem(DEFAULT_MOBILENUMBER.toString())));
    }

    @Test
    public void getEmployee() throws Exception {
        // Initialize the database
        employeeRepository.save(employee);

        // Get the employee
        restEmployeeMockMvc.perform(get("/api/employees/{id}", employee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(employee.getId().toString()))
            .andExpect(jsonPath("$.empid").value(DEFAULT_EMPID.toString()))
            .andExpect(jsonPath("$.empname").value(DEFAULT_EMPNAME.toString()))
            .andExpect(jsonPath("$.department").value(DEFAULT_DEPARTMENT.toString()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION.toString()))
            .andExpect(jsonPath("$.emailid").value(DEFAULT_EMAILID.toString()))
            .andExpect(jsonPath("$.dateofbirth").value(sameInstant(DEFAULT_DATEOFBIRTH)))
            .andExpect(jsonPath("$.dateofjoining").value(sameInstant(DEFAULT_DATEOFJOINING)))
            .andExpect(jsonPath("$.relievingdate").value(sameInstant(DEFAULT_RELIEVINGDATE)))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdate").value(sameInstant(DEFAULT_CREATEDATE)))
            .andExpect(jsonPath("$.updatedate").value(sameInstant(DEFAULT_UPDATEDATE)))
            .andExpect(jsonPath("$.mobilenumber").value(DEFAULT_MOBILENUMBER.toString()));
    }

    @Test
    public void getNonExistingEmployee() throws Exception {
        // Get the employee
        restEmployeeMockMvc.perform(get("/api/employees/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateEmployee() throws Exception {
        // Initialize the database
        employeeRepository.save(employee);
        employeeSearchRepository.save(employee);
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee
        Employee updatedEmployee = employeeRepository.findOne(employee.getId());
        updatedEmployee
                .empid(UPDATED_EMPID)
                .empname(UPDATED_EMPNAME)
                .department(UPDATED_DEPARTMENT)
                .designation(UPDATED_DESIGNATION)
                .emailid(UPDATED_EMAILID)
                .dateofbirth(UPDATED_DATEOFBIRTH)
                .dateofjoining(UPDATED_DATEOFJOINING)
                .relievingdate(UPDATED_RELIEVINGDATE)
                .active(UPDATED_ACTIVE)
                .createdate(UPDATED_CREATEDATE)
                .updatedate(UPDATED_UPDATEDATE)
                .mobilenumber(UPDATED_MOBILENUMBER);
        EmployeeDTO employeeDTO = employeeMapper.employeeToEmployeeDTO(updatedEmployee);

        restEmployeeMockMvc.perform(put("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getEmpid()).isEqualTo(UPDATED_EMPID);
        assertThat(testEmployee.getEmpname()).isEqualTo(UPDATED_EMPNAME);
        assertThat(testEmployee.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testEmployee.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testEmployee.getEmailid()).isEqualTo(UPDATED_EMAILID);
        assertThat(testEmployee.getDateofbirth()).isEqualTo(UPDATED_DATEOFBIRTH);
        assertThat(testEmployee.getDateofjoining()).isEqualTo(UPDATED_DATEOFJOINING);
        assertThat(testEmployee.getRelievingdate()).isEqualTo(UPDATED_RELIEVINGDATE);
        assertThat(testEmployee.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testEmployee.getCreatedate()).isEqualTo(UPDATED_CREATEDATE);
        assertThat(testEmployee.getUpdatedate()).isEqualTo(UPDATED_UPDATEDATE);
        assertThat(testEmployee.getMobilenumber()).isEqualTo(UPDATED_MOBILENUMBER);

        // Validate the Employee in Elasticsearch
        Employee employeeEs = employeeSearchRepository.findOne(testEmployee.getId());
        assertThat(employeeEs).isEqualToComparingFieldByField(testEmployee);
    }

    @Test
    public void updateNonExistingEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.employeeToEmployeeDTO(employee);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEmployeeMockMvc.perform(put("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isCreated());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteEmployee() throws Exception {
        // Initialize the database
        employeeRepository.save(employee);
        employeeSearchRepository.save(employee);
        int databaseSizeBeforeDelete = employeeRepository.findAll().size();

        // Get the employee
        restEmployeeMockMvc.perform(delete("/api/employees/{id}", employee.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean employeeExistsInEs = employeeSearchRepository.exists(employee.getId());
        assertThat(employeeExistsInEs).isFalse();

        // Validate the database is empty
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void searchEmployee() throws Exception {
        // Initialize the database
        employeeRepository.save(employee);
        employeeSearchRepository.save(employee);

        // Search the employee
        restEmployeeMockMvc.perform(get("/api/_search/employees?query=id:" + employee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().toString())))
            .andExpect(jsonPath("$.[*].empid").value(hasItem(DEFAULT_EMPID.toString())))
            .andExpect(jsonPath("$.[*].empname").value(hasItem(DEFAULT_EMPNAME.toString())))
            .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT.toString())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())))
            .andExpect(jsonPath("$.[*].emailid").value(hasItem(DEFAULT_EMAILID.toString())))
            .andExpect(jsonPath("$.[*].dateofbirth").value(hasItem(sameInstant(DEFAULT_DATEOFBIRTH))))
            .andExpect(jsonPath("$.[*].dateofjoining").value(hasItem(sameInstant(DEFAULT_DATEOFJOINING))))
            .andExpect(jsonPath("$.[*].relievingdate").value(hasItem(sameInstant(DEFAULT_RELIEVINGDATE))))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdate").value(hasItem(sameInstant(DEFAULT_CREATEDATE))))
            .andExpect(jsonPath("$.[*].updatedate").value(hasItem(sameInstant(DEFAULT_UPDATEDATE))))
            .andExpect(jsonPath("$.[*].mobilenumber").value(hasItem(DEFAULT_MOBILENUMBER.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Employee.class);
    }
}
