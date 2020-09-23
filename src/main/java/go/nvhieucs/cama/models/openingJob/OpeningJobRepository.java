package go.nvhieucs.cama.models.openingJob;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpeningJobRepository extends JpaRepository<OpeningJob,Long> {

}
