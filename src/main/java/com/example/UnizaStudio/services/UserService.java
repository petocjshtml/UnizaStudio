package com.example.UnizaStudio.services;

import com.example.UnizaStudio.models.data_transfer_objects.RegisterDTO;
import com.example.UnizaStudio.models.User;
import com.example.UnizaStudio.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String registerUser(RegisterDTO registerDTO) {
        if (userRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
            return "Email už existuje!";
        }
        if (userRepository.findByNickname(registerDTO.getNickname()).isPresent()) {
            return "Prezývka už existuje!";
        }
        if (!registerDTO.getPassword().equals(registerDTO.getPasswordRepeat())) {
            return "Heslá sa nezhodujú!";
        }

        User user = new User();
        user.setEmail(registerDTO.getEmail());
        user.setNickname(registerDTO.getNickname());
        // Telefónne číslo sa ukladá s predponou +421
        user.setPhone("+421" + registerDTO.getPhone());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        userRepository.save(user);
        return "OK";
    }
}
