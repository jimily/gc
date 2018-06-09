package cn.gc.repository;

import cn.gc.domain.ProjectSchedule;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the ProjectSchedule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectScheduleRepository extends JpaRepository<ProjectSchedule, Long> {

}
