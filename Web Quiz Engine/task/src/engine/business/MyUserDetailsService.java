package engine.business;

import engine.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Works with the spring security framework to authenticate user
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    /**
     * Recieves a username and cross references it with a database
     * @param s Username of the user
     * @return UserDetails that checks if the username/password combo is valid
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(s);
        return new MyUserDetails(user.orElseThrow(() -> new UsernameNotFoundException("Username not found")));
    }
}
