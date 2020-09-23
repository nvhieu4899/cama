package go.nvhieucs.cama.models.applicationUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<ApplicationUser, Long> {
    boolean existsByUsername(String username);
    ApplicationUser findByUsername(String username);
    ApplicationUser findOneByUsername(String username);
}
