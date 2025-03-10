package org.example.unizastudio.services;

import org.example.unizastudio.models.User;
import org.example.unizastudio.repositories.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageUploadService imageUploadService;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder, ImageUploadService imageUploadService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.imageUploadService = imageUploadService;
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

    // Overenie aktuálneho hesla
    public boolean checkPassword(Long userId, String rawPassword) {
        User user = userRepository.findById(userId).orElse(null);
        return user != null && passwordEncoder.matches(rawPassword, user.getPassword());
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    // Aktualizácia hesla
    @Transactional
    public void updatePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }


    @Transactional
    public boolean editProfile(Long id, String email, String nickname, MultipartFile photo) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) return false;

        if (email != null && !email.isEmpty()) {
            if (userRepository.existsByEmailAndIdNot(email, id)) {
                return false;
            }
            user.setEmail(email);
        }

        if (nickname != null && !nickname.isEmpty()) {
            user.setNickname(nickname);
        }

        if (photo != null && !photo.isEmpty()) {
            try {
                String imageUrl = imageUploadService.uploadImage(photo);
                user.setPhoto(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Image upload failed", e);
            }
        }

        userRepository.save(user);

        // Aktualizácia autentifikácie, ak sa zmenil email
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsernamePasswordAuthenticationToken newAuth =
                new UsernamePasswordAuthenticationToken(user.getEmail(), authentication.getCredentials(), authentication.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        return true;
    }

    @Transactional
    public boolean editUser(Long id, String email, String nickname, MultipartFile photo, Boolean isAdmin, String password) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) return false;

        if (email != null && !email.isEmpty()) {
            if (userRepository.existsByEmailAndIdNot(email, id)) {
                return false;
            }
            user.setEmail(email);
        }

        if (nickname != null && !nickname.isEmpty()) {
            user.setNickname(nickname);
        }

        if (isAdmin != null) {
            user.setAdmin(isAdmin);
        }

        if (password != null && !password.isEmpty()) {
            user.setPassword(passwordEncoder.encode(password));
        }

        if (photo != null && !photo.isEmpty()) {
            try {
                String imageUrl = imageUploadService.uploadImage(photo);
                user.setPhoto(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        userRepository.save(user);
        return true;
    }


    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
