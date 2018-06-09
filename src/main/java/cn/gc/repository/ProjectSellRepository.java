package cn.gc.repository;

import cn.gc.domain.ProjectSell;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the ProjectSell entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectSellRepository extends JpaRepository<ProjectSell, Long> {

}
