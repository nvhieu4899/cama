package go.nvhieucs.cama.models.applicationUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServices {
    @Autowired
    private UserRepository userRepository;

    @Cacheable("users")
    public List<ApplicationUser> findAll()
    {
        return userRepository.findAll();
    }

    @Cacheable(value = "user", key = "#p0")
    public Optional<ApplicationUser> findById(Long id)
    {
        return userRepository.findById(id);
    }

    @Cacheable(value = "existUser",key = "#p0")
    public boolean existsByUsername(String username)
    {
        return userRepository.existsByUsername(username);
    }

    @CacheEvict(value = "user",key = "#user.id")
    public ApplicationUser save(ApplicationUser user)
    {
        return userRepository.save(user);
    }

    @Cacheable(value = "user", key = "#p0")
    public ApplicationUser findOneByUsername(String username)
    {
        return userRepository.findOneByUsername(username);
    }
}
