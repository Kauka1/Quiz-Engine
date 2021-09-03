package engine.business;

import engine.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired(required = false)
    PasswordEncoder passwordEncoder;

    public void createUser(User user){
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        userRepository.save(user);
    }

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

