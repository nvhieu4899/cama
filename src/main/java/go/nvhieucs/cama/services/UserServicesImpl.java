package go.nvhieucs.cama.services;

import go.nvhieucs.cama.models.applicationUser.ApplicationUser;
import go.nvhieucs.cama.models.applicationUser.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserServicesImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        ApplicationUser user = userRepository.findByUsername(s);
        if (user != null) {
            return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException(s);
        }
    }
}
