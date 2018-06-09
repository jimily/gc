package cn.gc.web.rest;

import cn.gc.GcApp;

import cn.gc.domain.ProjectSchedule;
import cn.gc.repository.ProjectScheduleRepository;
import cn.gc.service.ProjectScheduleService;
import cn.gc.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.ArrayList;

import static cn.gc.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProjectScheduleResource REST controller.
 *
 * @see ProjectScheduleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GcApp.class)
public class ProjectScheduleResourceIntTest {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final Long DEFAULT_PROJECT_ID = 1L;
    private static final Long UPDATED_PROJECT_ID = 2L;

    private static final Long DEFAULT_BALANCE = 1L;
    private static final Long UPDATED_BALANCE = 2L;

    private static final String DEFAULT_TX_HASH = "AAAAAAAAAA";
    private static final String UPDATED_TX_HASH = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATE_TIME = 1L;
    private static final Long UPDATED_CREATE_TIME = 2L;

    private static final Long DEFAULT_UPDATE_TIME = 1L;
    private static final Long UPDATED_UPDATE_TIME = 2L;

    @Autowired
    private ProjectScheduleRepository projectScheduleRepository;

    

    @Autowired
    private ProjectScheduleService projectScheduleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProjectScheduleMockMvc;

    private ProjectSchedule projectSchedule;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjectScheduleResource projectScheduleResource = new ProjectScheduleResource(projectScheduleService);
        this.restProjectScheduleMockMvc = MockMvcBuilders.standaloneSetup(projectScheduleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectSchedule createEntity(EntityManager em) {
        ProjectSchedule projectSchedule = new ProjectSchedule()
            .userId(DEFAULT_USER_ID)
            .projectId(DEFAULT_PROJECT_ID)
            .balance(DEFAULT_BALANCE)
            .txHash(DEFAULT_TX_HASH)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME);
        return projectSchedule;
    }

    @Before
    public void initTest() {
        projectSchedule = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjectSchedule() throws Exception {
        int databaseSizeBeforeCreate = projectScheduleRepository.findAll().size();

        // Create the ProjectSchedule
        restProjectScheduleMockMvc.perform(post("/api/project-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectSchedule)))
            .andExpect(status().isCreated());

        // Validate the ProjectSchedule in the database
        List<ProjectSchedule> projectScheduleList = projectScheduleRepository.findAll();
        assertThat(projectScheduleList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectSchedule testProjectSchedule = projectScheduleList.get(projectScheduleList.size() - 1);
        assertThat(testProjectSchedule.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testProjectSchedule.getProjectId()).isEqualTo(DEFAULT_PROJECT_ID);
        assertThat(testProjectSchedule.getBalance()).isEqualTo(DEFAULT_BALANCE);
        assertThat(testProjectSchedule.getTxHash()).isEqualTo(DEFAULT_TX_HASH);
        assertThat(testProjectSchedule.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testProjectSchedule.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createProjectScheduleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectScheduleRepository.findAll().size();

        // Create the ProjectSchedule with an existing ID
        projectSchedule.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectScheduleMockMvc.perform(post("/api/project-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectSchedule)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectSchedule in the database
        List<ProjectSchedule> projectScheduleList = projectScheduleRepository.findAll();
        assertThat(projectScheduleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectScheduleRepository.findAll().size();
        // set the field null
        projectSchedule.setUserId(null);

        // Create the ProjectSchedule, which fails.

        restProjectScheduleMockMvc.perform(post("/api/project-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectSchedule)))
            .andExpect(status().isBadRequest());

        List<ProjectSchedule> projectScheduleList = projectScheduleRepository.findAll();
        assertThat(projectScheduleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProjectIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectScheduleRepository.findAll().size();
        // set the field null
        projectSchedule.setProjectId(null);

        // Create the ProjectSchedule, which fails.

        restProjectScheduleMockMvc.perform(post("/api/project-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectSchedule)))
            .andExpect(status().isBadRequest());

        List<ProjectSchedule> projectScheduleList = projectScheduleRepository.findAll();
        assertThat(projectScheduleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBalanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectScheduleRepository.findAll().size();
        // set the field null
        projectSchedule.setBalance(null);

        // Create the ProjectSchedule, which fails.

        restProjectScheduleMockMvc.perform(post("/api/project-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectSchedule)))
            .andExpect(status().isBadRequest());

        List<ProjectSchedule> projectScheduleList = projectScheduleRepository.findAll();
        assertThat(projectScheduleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTxHashIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectScheduleRepository.findAll().size();
        // set the field null
        projectSchedule.setTxHash(null);

        // Create the ProjectSchedule, which fails.

        restProjectScheduleMockMvc.perform(post("/api/project-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectSchedule)))
            .andExpect(status().isBadRequest());

        List<ProjectSchedule> projectScheduleList = projectScheduleRepository.findAll();
        assertThat(projectScheduleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectScheduleRepository.findAll().size();
        // set the field null
        projectSchedule.setCreateTime(null);

        // Create the ProjectSchedule, which fails.

        restProjectScheduleMockMvc.perform(post("/api/project-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectSchedule)))
            .andExpect(status().isBadRequest());

        List<ProjectSchedule> projectScheduleList = projectScheduleRepository.findAll();
        assertThat(projectScheduleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectScheduleRepository.findAll().size();
        // set the field null
        projectSchedule.setUpdateTime(null);

        // Create the ProjectSchedule, which fails.

        restProjectScheduleMockMvc.perform(post("/api/project-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectSchedule)))
            .andExpect(status().isBadRequest());

        List<ProjectSchedule> projectScheduleList = projectScheduleRepository.findAll();
        assertThat(projectScheduleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProjectSchedules() throws Exception {
        // Initialize the database
        projectScheduleRepository.saveAndFlush(projectSchedule);

        // Get all the projectScheduleList
        restProjectScheduleMockMvc.perform(get("/api/project-schedules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectSchedule.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].projectId").value(hasItem(DEFAULT_PROJECT_ID.intValue())))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.intValue())))
            .andExpect(jsonPath("$.[*].txHash").value(hasItem(DEFAULT_TX_HASH.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.intValue())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.intValue())));
    }
    

    @Test
    @Transactional
    public void getProjectSchedule() throws Exception {
        // Initialize the database
        projectScheduleRepository.saveAndFlush(projectSchedule);

        // Get the projectSchedule
        restProjectScheduleMockMvc.perform(get("/api/project-schedules/{id}", projectSchedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projectSchedule.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.projectId").value(DEFAULT_PROJECT_ID.intValue()))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE.intValue()))
            .andExpect(jsonPath("$.txHash").value(DEFAULT_TX_HASH.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.intValue()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingProjectSchedule() throws Exception {
        // Get the projectSchedule
        restProjectScheduleMockMvc.perform(get("/api/project-schedules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectSchedule() throws Exception {
        // Initialize the database
        projectScheduleService.save(projectSchedule);

        int databaseSizeBeforeUpdate = projectScheduleRepository.findAll().size();

        // Update the projectSchedule
        ProjectSchedule updatedProjectSchedule = projectScheduleRepository.findById(projectSchedule.getId()).get();
        // Disconnect from session so that the updates on updatedProjectSchedule are not directly saved in db
        em.detach(updatedProjectSchedule);
        updatedProjectSchedule
            .userId(UPDATED_USER_ID)
            .projectId(UPDATED_PROJECT_ID)
            .balance(UPDATED_BALANCE)
            .txHash(UPDATED_TX_HASH)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME);

        restProjectScheduleMockMvc.perform(put("/api/project-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjectSchedule)))
            .andExpect(status().isOk());

        // Validate the ProjectSchedule in the database
        List<ProjectSchedule> projectScheduleList = projectScheduleRepository.findAll();
        assertThat(projectScheduleList).hasSize(databaseSizeBeforeUpdate);
        ProjectSchedule testProjectSchedule = projectScheduleList.get(projectScheduleList.size() - 1);
        assertThat(testProjectSchedule.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testProjectSchedule.getProjectId()).isEqualTo(UPDATED_PROJECT_ID);
        assertThat(testProjectSchedule.getBalance()).isEqualTo(UPDATED_BALANCE);
        assertThat(testProjectSchedule.getTxHash()).isEqualTo(UPDATED_TX_HASH);
        assertThat(testProjectSchedule.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testProjectSchedule.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingProjectSchedule() throws Exception {
        int databaseSizeBeforeUpdate = projectScheduleRepository.findAll().size();

        // Create the ProjectSchedule

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProjectScheduleMockMvc.perform(put("/api/project-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectSchedule)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectSchedule in the database
        List<ProjectSchedule> projectScheduleList = projectScheduleRepository.findAll();
        assertThat(projectScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProjectSchedule() throws Exception {
        // Initialize the database
        projectScheduleService.save(projectSchedule);

        int databaseSizeBeforeDelete = projectScheduleRepository.findAll().size();

        // Get the projectSchedule
        restProjectScheduleMockMvc.perform(delete("/api/project-schedules/{id}", projectSchedule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProjectSchedule> projectScheduleList = projectScheduleRepository.findAll();
        assertThat(projectScheduleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectSchedule.class);
        ProjectSchedule projectSchedule1 = new ProjectSchedule();
        projectSchedule1.setId(1L);
        ProjectSchedule projectSchedule2 = new ProjectSchedule();
        projectSchedule2.setId(projectSchedule1.getId());
        assertThat(projectSchedule1).isEqualTo(projectSchedule2);
        projectSchedule2.setId(2L);
        assertThat(projectSchedule1).isNotEqualTo(projectSchedule2);
        projectSchedule1.setId(null);
        assertThat(projectSchedule1).isNotEqualTo(projectSchedule2);
    }
}
