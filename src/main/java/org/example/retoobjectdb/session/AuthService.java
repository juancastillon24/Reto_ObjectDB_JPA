package org.example.retoobjectdb.session;

import org.example.retoobjectdb.models.User;
import org.example.retoobjectdb.repositories.UserRepository;

import java.util.Optional;

public class AuthService {

    UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> validateUser(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            if (user.get().getPassword().equals(password)) {
                return user;
            } else  {
                return Optional.empty();
            }
        }
        return user;
    }
}
