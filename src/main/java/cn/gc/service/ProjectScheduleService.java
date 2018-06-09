package cn.gc.service;

import cn.gc.domain.ProjectSchedule;
import cn.gc.repository.ProjectScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing ProjectSchedule.
 */
@Service
@Transactional
public class ProjectScheduleService {

    private final Logger log = LoggerFactory.getLogger(ProjectScheduleService.class);

    private final ProjectScheduleRepository projectScheduleRepository;

    public ProjectScheduleService(ProjectScheduleRepository projectScheduleRepository) {
        this.projectScheduleRepository = projectScheduleRepository;
    }

    /**
     * Save a projectSchedule.
     *
     * @param projectSchedule the entity to save
     * @return the persisted entity
     */
    public ProjectSchedule save(ProjectSchedule projectSchedule) {
        log.debug("Request to save ProjectSchedule : {}", projectSchedule);
        return projectScheduleRepository.save(projectSchedule);
    }

    /**
     * Get all the projectSchedules.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ProjectSchedule> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectSchedules");
        return projectScheduleRepository.findAll(pageable);
    }


    /**
     * Get one projectSchedule by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ProjectSchedule> findOne(Long id) {
        log.debug("Request to get ProjectSchedule : {}", id);
        return projectScheduleRepository.findById(id);
    }

    /**
     * Delete the projectSchedule by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjectSchedule : {}", id);
        projectScheduleRepository.deleteById(id);
    }
}
