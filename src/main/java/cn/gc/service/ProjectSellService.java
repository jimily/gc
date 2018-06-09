package cn.gc.service;

import cn.gc.domain.ProjectSell;
import cn.gc.repository.ProjectSellRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing ProjectSell.
 */
@Service
@Transactional
public class ProjectSellService {

    private final Logger log = LoggerFactory.getLogger(ProjectSellService.class);

    private final ProjectSellRepository projectSellRepository;

    public ProjectSellService(ProjectSellRepository projectSellRepository) {
        this.projectSellRepository = projectSellRepository;
    }

    /**
     * Save a projectSell.
     *
     * @param projectSell the entity to save
     * @return the persisted entity
     */
    public ProjectSell save(ProjectSell projectSell) {
        log.debug("Request to save ProjectSell : {}", projectSell);
        return projectSellRepository.save(projectSell);
    }

    /**
     * Get all the projectSells.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ProjectSell> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectSells");
        return projectSellRepository.findAll(pageable);
    }


    /**
     * Get one projectSell by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ProjectSell> findOne(Long id) {
        log.debug("Request to get ProjectSell : {}", id);
        return projectSellRepository.findById(id);
    }

    /**
     * Delete the projectSell by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjectSell : {}", id);
        projectSellRepository.deleteById(id);
    }
}
