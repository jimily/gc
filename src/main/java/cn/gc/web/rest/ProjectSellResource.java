package cn.gc.web.rest;

import cn.gc.domain.User;
import cn.gc.repository.UserRepository;
import cn.gc.security.SecurityUtils;
import cn.gc.web.rest.errors.InternalServerErrorException;
import com.codahale.metrics.annotation.Timed;
import cn.gc.domain.ProjectSell;
import cn.gc.service.ProjectSellService;
import cn.gc.web.rest.errors.BadRequestAlertException;
import cn.gc.web.rest.util.HeaderUtil;
import cn.gc.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ProjectSell.
 */
@RestController
@RequestMapping("/api")
public class ProjectSellResource {

    private final Logger log = LoggerFactory.getLogger(ProjectSellResource.class);

    private static final String ENTITY_NAME = "projectSell";

    private final ProjectSellService projectSellService;

    @Autowired
    private UserRepository userRepository;

    public ProjectSellResource(ProjectSellService projectSellService) {
        this.projectSellService = projectSellService;
    }

    /**
     * POST  /project-sells : Create a new projectSell.
     *
     * @param projectSell the projectSell to create
     * @return the ResponseEntity with status 201 (Created) and with body the new projectSell, or with status 400 (Bad Request) if the projectSell has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/project-sells")
    @Timed
    public ResponseEntity<ProjectSell> createProjectSell(@RequestBody ProjectSell projectSell) throws URISyntaxException {
        log.debug("REST request to save ProjectSell : {}", projectSell);
        if (projectSell.getId() != null) {
            throw new BadRequestAlertException("A new projectSell cannot already have an ID", ENTITY_NAME, "idexists");
        }
        final String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new InternalServerErrorException("Current user login not found"));
        Optional<User> user = userRepository.findOneByLogin(userLogin);
        projectSell.setOwnerUserId(user.get().getId());
        // 0 代表未被购买
        projectSell.setIsSell(0);
        projectSell.setBuyerId(0L);

        Long now = System.currentTimeMillis();
        projectSell.setCreateTime(now);
        projectSell.setUpdateTime(now);
        // 任意版本交易类型
        projectSell.setCopyrightSellType(1);
        ProjectSell result = projectSellService.save(projectSell);
        return ResponseEntity.created(new URI("/api/project-sells/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /project-sells : Updates an existing projectSell.
     *
     * @param projectSell the projectSell to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated projectSell,
     * or with status 400 (Bad Request) if the projectSell is not valid,
     * or with status 500 (Internal Server Error) if the projectSell couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/project-sells")
    @Timed
    public ResponseEntity<ProjectSell> updateProjectSell(@Valid @RequestBody ProjectSell projectSell) throws URISyntaxException {
        log.debug("REST request to update ProjectSell : {}", projectSell);
        if (projectSell.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectSell result = projectSellService.save(projectSell);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, projectSell.getId().toString()))
            .body(result);
    }

    /**
     * GET  /project-sells : get all the projectSells.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of projectSells in body
     */
    @GetMapping("/project-sells")
    @Timed
    public ResponseEntity<List<ProjectSell>> getAllProjectSells(Pageable pageable) {
        log.debug("REST request to get a page of ProjectSells");
        Page<ProjectSell> page = projectSellService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/project-sells");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /project-sells/:id : get the "id" projectSell.
     *
     * @param id the id of the projectSell to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the projectSell, or with status 404 (Not Found)
     */
    @GetMapping("/project-sells/{id}")
    @Timed
    public ResponseEntity<ProjectSell> getProjectSell(@PathVariable Long id) {
        log.debug("REST request to get ProjectSell : {}", id);
        Optional<ProjectSell> projectSell = projectSellService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectSell);
    }

    /**
     * DELETE  /project-sells/:id : delete the "id" projectSell.
     *
     * @param id the id of the projectSell to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/project-sells/{id}")
    @Timed
    public ResponseEntity<Void> deleteProjectSell(@PathVariable Long id) {
        log.debug("REST request to delete ProjectSell : {}", id);
        projectSellService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
