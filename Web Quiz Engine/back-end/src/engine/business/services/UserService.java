package engine.business.services;

import engine.business.models.User;
import engine.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

//Class that is part of the Spring Security module
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired(required = false)
    PasswordEncoder passwordEncoder;

    //creates user in database
    public void createUser(User user){
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        userRepository.save(user);
    }

    //gets the email from database
    public boolean emailExists(User user){
        Optional<User> found = userRepository.findByEmail(user.getEmail());
        return found.isPresent();
    }

    public Optional<User> getUser(){
        return userRepository.findById(0);
    }

    public Optional<User> getUserThroughEmail(String email){
        return userRepository.findByEmail(email);
    }
}

