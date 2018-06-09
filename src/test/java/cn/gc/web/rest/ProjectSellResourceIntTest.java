package cn.gc.web.rest;

import cn.gc.GcApp;

import cn.gc.domain.ProjectSell;
import cn.gc.repository.ProjectSellRepository;
import cn.gc.service.ProjectSellService;
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
 * Test class for the ProjectSellResource REST controller.
 *
 * @see ProjectSellResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GcApp.class)
public class ProjectSellResourceIntTest {

    private static final Long DEFAULT_PROJECT_ID = 1L;
    private static final Long UPDATED_PROJECT_ID = 2L;

    private static final Integer DEFAULT_COPYRIGHT_SELL_TYPE = 1;
    private static final Integer UPDATED_COPYRIGHT_SELL_TYPE = 2;

    private static final Long DEFAULT_SELLING_PRICE = 1L;
    private static final Long UPDATED_SELLING_PRICE = 2L;

    private static final String DEFAULT_TX_HASH = "AAAAAAAAAA";
    private static final String UPDATED_TX_HASH = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_OWNER_USER_ID = 1L;
    private static final Long UPDATED_OWNER_USER_ID = 2L;

    private static final Long DEFAULT_BUYER_ID = 1L;
    private static final Long UPDATED_BUYER_ID = 2L;

    private static final Integer DEFAULT_IS_SELL = 1;
    private static final Integer UPDATED_IS_SELL = 2;

    private static final Long DEFAULT_CREATE_TIME = 1L;
    private static final Long UPDATED_CREATE_TIME = 2L;

    private static final Long DEFAULT_UPDATE_TIME = 1L;
    private static final Long UPDATED_UPDATE_TIME = 2L;

    @Autowired
    private ProjectSellRepository projectSellRepository;

    

    @Autowired
    private ProjectSellService projectSellService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProjectSellMockMvc;

    private ProjectSell projectSell;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjectSellResource projectSellResource = new ProjectSellResource(projectSellService);
        this.restProjectSellMockMvc = MockMvcBuilders.standaloneSetup(projectSellResource)
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
    public static ProjectSell createEntity(EntityManager em) {
        ProjectSell projectSell = new ProjectSell()
            .projectId(DEFAULT_PROJECT_ID)
            .copyrightSellType(DEFAULT_COPYRIGHT_SELL_TYPE)
            .sellingPrice(DEFAULT_SELLING_PRICE)
            .txHash(DEFAULT_TX_HASH)
            .description(DEFAULT_DESCRIPTION)
            .ownerUserId(DEFAULT_OWNER_USER_ID)
            .buyerId(DEFAULT_BUYER_ID)
            .isSell(DEFAULT_IS_SELL)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME);
        return projectSell;
    }

    @Before
    public void initTest() {
        projectSell = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjectSell() throws Exception {
        int databaseSizeBeforeCreate = projectSellRepository.findAll().size();

        // Create the ProjectSell
        restProjectSellMockMvc.perform(post("/api/project-sells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectSell)))
            .andExpect(status().isCreated());

        // Validate the ProjectSell in the database
        List<ProjectSell> projectSellList = projectSellRepository.findAll();
        assertThat(projectSellList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectSell testProjectSell = projectSellList.get(projectSellList.size() - 1);
        assertThat(testProjectSell.getProjectId()).isEqualTo(DEFAULT_PROJECT_ID);
        assertThat(testProjectSell.getCopyrightSellType()).isEqualTo(DEFAULT_COPYRIGHT_SELL_TYPE);
        assertThat(testProjectSell.getSellingPrice()).isEqualTo(DEFAULT_SELLING_PRICE);
        assertThat(testProjectSell.getTxHash()).isEqualTo(DEFAULT_TX_HASH);
        assertThat(testProjectSell.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProjectSell.getOwnerUserId()).isEqualTo(DEFAULT_OWNER_USER_ID);
        assertThat(testProjectSell.getBuyerId()).isEqualTo(DEFAULT_BUYER_ID);
        assertThat(testProjectSell.getIsSell()).isEqualTo(DEFAULT_IS_SELL);
        assertThat(testProjectSell.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testProjectSell.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createProjectSellWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectSellRepository.findAll().size();

        // Create the ProjectSell with an existing ID
        projectSell.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectSellMockMvc.perform(post("/api/project-sells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectSell)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectSell in the database
        List<ProjectSell> projectSellList = projectSellRepository.findAll();
        assertThat(projectSellList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkProjectIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectSellRepository.findAll().size();
        // set the field null
        projectSell.setProjectId(null);

        // Create the ProjectSell, which fails.

        restProjectSellMockMvc.perform(post("/api/project-sells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectSell)))
            .andExpect(status().isBadRequest());

        List<ProjectSell> projectSellList = projectSellRepository.findAll();
        assertThat(projectSellList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCopyrightSellTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectSellRepository.findAll().size();
        // set the field null
        projectSell.setCopyrightSellType(null);

        // Create the ProjectSell, which fails.

        restProjectSellMockMvc.perform(post("/api/project-sells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectSell)))
            .andExpect(status().isBadRequest());

        List<ProjectSell> projectSellList = projectSellRepository.findAll();
        assertThat(projectSellList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSellingPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectSellRepository.findAll().size();
        // set the field null
        projectSell.setSellingPrice(null);

        // Create the ProjectSell, which fails.

        restProjectSellMockMvc.perform(post("/api/project-sells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectSell)))
            .andExpect(status().isBadRequest());

        List<ProjectSell> projectSellList = projectSellRepository.findAll();
        assertThat(projectSellList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTxHashIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectSellRepository.findAll().size();
        // set the field null
        projectSell.setTxHash(null);

        // Create the ProjectSell, which fails.

        restProjectSellMockMvc.perform(post("/api/project-sells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectSell)))
            .andExpect(status().isBadRequest());

        List<ProjectSell> projectSellList = projectSellRepository.findAll();
        assertThat(projectSellList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectSellRepository.findAll().size();
        // set the field null
        projectSell.setDescription(null);

        // Create the ProjectSell, which fails.

        restProjectSellMockMvc.perform(post("/api/project-sells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectSell)))
            .andExpect(status().isBadRequest());

        List<ProjectSell> projectSellList = projectSellRepository.findAll();
        assertThat(projectSellList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOwnerUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectSellRepository.findAll().size();
        // set the field null
        projectSell.setOwnerUserId(null);

        // Create the ProjectSell, which fails.

        restProjectSellMockMvc.perform(post("/api/project-sells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectSell)))
            .andExpect(status().isBadRequest());

        List<ProjectSell> projectSellList = projectSellRepository.findAll();
        assertThat(projectSellList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBuyerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectSellRepository.findAll().size();
        // set the field null
        projectSell.setBuyerId(null);

        // Create the ProjectSell, which fails.

        restProjectSellMockMvc.perform(post("/api/project-sells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectSell)))
            .andExpect(status().isBadRequest());

        List<ProjectSell> projectSellList = projectSellRepository.findAll();
        assertThat(projectSellList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsSellIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectSellRepository.findAll().size();
        // set the field null
        projectSell.setIsSell(null);

        // Create the ProjectSell, which fails.

        restProjectSellMockMvc.perform(post("/api/project-sells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectSell)))
            .andExpect(status().isBadRequest());

        List<ProjectSell> projectSellList = projectSellRepository.findAll();
        assertThat(projectSellList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectSellRepository.findAll().size();
        // set the field null
        projectSell.setCreateTime(null);

        // Create the ProjectSell, which fails.

        restProjectSellMockMvc.perform(post("/api/project-sells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectSell)))
            .andExpect(status().isBadRequest());

        List<ProjectSell> projectSellList = projectSellRepository.findAll();
        assertThat(projectSellList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectSellRepository.findAll().size();
        // set the field null
        projectSell.setUpdateTime(null);

        // Create the ProjectSell, which fails.

        restProjectSellMockMvc.perform(post("/api/project-sells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectSell)))
            .andExpect(status().isBadRequest());

        List<ProjectSell> projectSellList = projectSellRepository.findAll();
        assertThat(projectSellList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProjectSells() throws Exception {
        // Initialize the database
        projectSellRepository.saveAndFlush(projectSell);

        // Get all the projectSellList
        restProjectSellMockMvc.perform(get("/api/project-sells?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectSell.getId().intValue())))
            .andExpect(jsonPath("$.[*].projectId").value(hasItem(DEFAULT_PROJECT_ID.intValue())))
            .andExpect(jsonPath("$.[*].copyrightSellType").value(hasItem(DEFAULT_COPYRIGHT_SELL_TYPE)))
            .andExpect(jsonPath("$.[*].sellingPrice").value(hasItem(DEFAULT_SELLING_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].txHash").value(hasItem(DEFAULT_TX_HASH.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].ownerUserId").value(hasItem(DEFAULT_OWNER_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].buyerId").value(hasItem(DEFAULT_BUYER_ID.intValue())))
            .andExpect(jsonPath("$.[*].isSell").value(hasItem(DEFAULT_IS_SELL)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.intValue())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.intValue())));
    }
    

    @Test
    @Transactional
    public void getProjectSell() throws Exception {
        // Initialize the database
        projectSellRepository.saveAndFlush(projectSell);

        // Get the projectSell
        restProjectSellMockMvc.perform(get("/api/project-sells/{id}", projectSell.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projectSell.getId().intValue()))
            .andExpect(jsonPath("$.projectId").value(DEFAULT_PROJECT_ID.intValue()))
            .andExpect(jsonPath("$.copyrightSellType").value(DEFAULT_COPYRIGHT_SELL_TYPE))
            .andExpect(jsonPath("$.sellingPrice").value(DEFAULT_SELLING_PRICE.intValue()))
            .andExpect(jsonPath("$.txHash").value(DEFAULT_TX_HASH.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.ownerUserId").value(DEFAULT_OWNER_USER_ID.intValue()))
            .andExpect(jsonPath("$.buyerId").value(DEFAULT_BUYER_ID.intValue()))
            .andExpect(jsonPath("$.isSell").value(DEFAULT_IS_SELL))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.intValue()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingProjectSell() throws Exception {
        // Get the projectSell
        restProjectSellMockMvc.perform(get("/api/project-sells/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectSell() throws Exception {
        // Initialize the database
        projectSellService.save(projectSell);

        int databaseSizeBeforeUpdate = projectSellRepository.findAll().size();

        // Update the projectSell
        ProjectSell updatedProjectSell = projectSellRepository.findById(projectSell.getId()).get();
        // Disconnect from session so that the updates on updatedProjectSell are not directly saved in db
        em.detach(updatedProjectSell);
        updatedProjectSell
            .projectId(UPDATED_PROJECT_ID)
            .copyrightSellType(UPDATED_COPYRIGHT_SELL_TYPE)
            .sellingPrice(UPDATED_SELLING_PRICE)
            .txHash(UPDATED_TX_HASH)
            .description(UPDATED_DESCRIPTION)
            .ownerUserId(UPDATED_OWNER_USER_ID)
            .buyerId(UPDATED_BUYER_ID)
            .isSell(UPDATED_IS_SELL)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME);

        restProjectSellMockMvc.perform(put("/api/project-sells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjectSell)))
            .andExpect(status().isOk());

        // Validate the ProjectSell in the database
        List<ProjectSell> projectSellList = projectSellRepository.findAll();
        assertThat(projectSellList).hasSize(databaseSizeBeforeUpdate);
        ProjectSell testProjectSell = projectSellList.get(projectSellList.size() - 1);
        assertThat(testProjectSell.getProjectId()).isEqualTo(UPDATED_PROJECT_ID);
        assertThat(testProjectSell.getCopyrightSellType()).isEqualTo(UPDATED_COPYRIGHT_SELL_TYPE);
        assertThat(testProjectSell.getSellingPrice()).isEqualTo(UPDATED_SELLING_PRICE);
        assertThat(testProjectSell.getTxHash()).isEqualTo(UPDATED_TX_HASH);
        assertThat(testProjectSell.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProjectSell.getOwnerUserId()).isEqualTo(UPDATED_OWNER_USER_ID);
        assertThat(testProjectSell.getBuyerId()).isEqualTo(UPDATED_BUYER_ID);
        assertThat(testProjectSell.getIsSell()).isEqualTo(UPDATED_IS_SELL);
        assertThat(testProjectSell.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testProjectSell.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingProjectSell() throws Exception {
        int databaseSizeBeforeUpdate = projectSellRepository.findAll().size();

        // Create the ProjectSell

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProjectSellMockMvc.perform(put("/api/project-sells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectSell)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectSell in the database
        List<ProjectSell> projectSellList = projectSellRepository.findAll();
        assertThat(projectSellList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProjectSell() throws Exception {
        // Initialize the database
        projectSellService.save(projectSell);

        int databaseSizeBeforeDelete = projectSellRepository.findAll().size();

        // Get the projectSell
        restProjectSellMockMvc.perform(delete("/api/project-sells/{id}", projectSell.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProjectSell> projectSellList = projectSellRepository.findAll();
        assertThat(projectSellList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectSell.class);
        ProjectSell projectSell1 = new ProjectSell();
        projectSell1.setId(1L);
        ProjectSell projectSell2 = new ProjectSell();
        projectSell2.setId(projectSell1.getId());
        assertThat(projectSell1).isEqualTo(projectSell2);
        projectSell2.setId(2L);
        assertThat(projectSell1).isNotEqualTo(projectSell2);
        projectSell1.setId(null);
        assertThat(projectSell1).isNotEqualTo(projectSell2);
    }
}
