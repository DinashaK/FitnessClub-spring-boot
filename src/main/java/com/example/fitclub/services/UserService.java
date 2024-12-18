package com.example.fitclub.services;

import com.example.fitclub.models.User;
import com.example.fitclub.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User getUserById(Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(null);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    // Метод для сохранения пользователя
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Проверка пароля
    public boolean checkPassword(User user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

    // Получение всех пользователей
    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    // Получение пользователя по email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Метод для получения пользователя по username
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Получение всех пользователей в виде списка
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Обновление роли пользователя
    public void updateUserRole(Integer userId, String role) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
        user.setRole(role);
        userRepository.save(user);
    }
}
