package com.nic.nicissuingplatform.service;

import com.nic.nicissuingplatform.entity.Role;
import com.nic.nicissuingplatform.entity.User;
import com.nic.nicissuingplatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✓ Checks if user exists
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public List<User> getAllAdmins() {
        return userRepository.findAll().stream().filter(u -> u.getRole() == Role.ROLE_ADMIN).toList();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public void createAdmin(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));  // ✓ Password encryption
        user.setRole(Role.ROLE_ADMIN);
        userRepository.save(user);
    }

    public void deleteAdmin(Long id) {
        userRepository.deleteById(id);
    }
}
