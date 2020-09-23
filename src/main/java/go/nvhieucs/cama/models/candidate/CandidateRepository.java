package go.nvhieucs.cama.models.candidate;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface CandidateRepository extends PagingAndSortingRepository<Candidate, BigInteger> {
    @Query("SELECT c.email from candidate c")
    List<String> getAllCandidateEmail();
}
