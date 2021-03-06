package go.nvhieucs.cama.models.job;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job,Long> {
    List<Job> findByLevelIgnoreCase(String level);
    List<Job> findByJobNameContainsIgnoreCase(String name);
}
