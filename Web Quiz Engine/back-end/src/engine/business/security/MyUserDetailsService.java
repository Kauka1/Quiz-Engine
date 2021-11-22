package engine.business.security;

import engine.business.models.User;
import engine.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

//This class is part of the spring secutiry module and uses the user and userdetails entites to check if the inputted
//password is correct or not
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(s);
        return new MyUserDetails(user.orElseThrow(() -> new UsernameNotFoundException("Username not found")));
    }
}


