package cn.gc.web.rest;

import com.codahale.metrics.annotation.Timed;
import cn.gc.domain.ProjectSchedule;
import cn.gc.service.ProjectScheduleService;
import cn.gc.web.rest.errors.BadRequestAlertException;
import cn.gc.web.rest.util.HeaderUtil;
import cn.gc.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing ProjectSchedule.
 */
@RestController
@RequestMapping("/api")
public class ProjectScheduleResource {

    private final Logger log = LoggerFactory.getLogger(ProjectScheduleResource.class);

    private static final String ENTITY_NAME = "projectSchedule";

    private final ProjectScheduleService projectScheduleService;

    public ProjectScheduleResource(ProjectScheduleService projectScheduleService) {
        this.projectScheduleService = projectScheduleService;
    }

    /**
     * POST  /project-schedules : Create a new projectSchedule.
     *
     * @param projectSchedule the projectSchedule to create
     * @return the ResponseEntity with status 201 (Created) and with body the new projectSchedule, or with status 400 (Bad Request) if the projectSchedule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/project-schedules")
    @Timed
    public ResponseEntity<ProjectSchedule> createProjectSchedule(@Valid @RequestBody ProjectSchedule projectSchedule) throws URISyntaxException {
        log.debug("REST request to save ProjectSchedule : {}", projectSchedule);
        if (projectSchedule.getId() != null) {
            throw new BadRequestAlertException("A new projectSchedule cannot already have an ID", ENTITY_NAME, "idexists");
        }        
        ProjectSchedule result = projectScheduleService.save(projectSchedule);
        return ResponseEntity.created(new URI("/api/project-schedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /project-schedules : Updates an existing projectSchedule.
     *
     * @param projectSchedule the projectSchedule to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated projectSchedule,
     * or with status 400 (Bad Request) if the projectSchedule is not valid,
     * or with status 500 (Internal Server Error) if the projectSchedule couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/project-schedules")
    @Timed
    public ResponseEntity<ProjectSchedule> updateProjectSchedule(@Valid @RequestBody ProjectSchedule projectSchedule) throws URISyntaxException {
        log.debug("REST request to update ProjectSchedule : {}", projectSchedule);
        if (projectSchedule.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }        
        ProjectSchedule result = projectScheduleService.save(projectSchedule);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, projectSchedule.getId().toString()))
            .body(result);
    }

    /**
     * GET  /project-schedules : get all the projectSchedules.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of projectSchedules in body
     */
    @GetMapping("/project-schedules")
    @Timed
    public ResponseEntity<List<ProjectSchedule>> getAllProjectSchedules(Pageable pageable) {
        log.debug("REST request to get a page of ProjectSchedules");
        Page<ProjectSchedule> page = projectScheduleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/project-schedules");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /project-schedules/:id : get the "id" projectSchedule.
     *
     * @param id the id of the projectSchedule to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the projectSchedule, or with status 404 (Not Found)
     */
    @GetMapping("/project-schedules/{id}")
    @Timed
    public ResponseEntity<ProjectSchedule> getProjectSchedule(@PathVariable Long id) {
        log.debug("REST request to get ProjectSchedule : {}", id);
        Optional<ProjectSchedule> projectSchedule = projectScheduleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectSchedule);
    }

    /**
     * DELETE  /project-schedules/:id : delete the "id" projectSchedule.
     *
     * @param id the id of the projectSchedule to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/project-schedules/{id}")
    @Timed
    public ResponseEntity<Void> deleteProjectSchedule(@PathVariable Long id) {
        log.debug("REST request to delete ProjectSchedule : {}", id);
        projectScheduleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
