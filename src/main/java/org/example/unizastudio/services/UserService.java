package org.example.unizastudio.services;

import org.example.unizastudio.models.User;
import org.example.unizastudio.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public boolean registerNewUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return false;
        }
        user.setAdmin(false);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        users.forEach(user -> user.setPassword(null));
        return users;
    }

    public User getCurrentUserSafe() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) {
            return null;
        }
        User user = userRepository.findByEmail(auth.getName()).orElse(null);
        if (user != null) {
            user.setPassword(null);
        }
        return user;
    }

    @Transactional
    public boolean editUser(Long id, String email, String nickname, String phone, boolean isAdmin, String password) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return false;
        }
        user.setEmail(email);
        user.setNickname(nickname);
        user.setPhone(phone);
        user.setAdmin(isAdmin);
        if (password != null && !password.isEmpty()) {
            user.setPassword(passwordEncoder.encode(password));
        }
        userRepository.save(user);
        return true;
    }


    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
