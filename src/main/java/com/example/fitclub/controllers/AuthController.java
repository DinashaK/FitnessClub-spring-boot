package com.example.fitclub.controllers;

import com.example.fitclub.models.*;
import com.example.fitclub.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user,
                               @RequestParam("role") String role,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        if (role == null || role.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Выберите роль");
            return "redirect:/register";
        }

        // Проверка на наличие уже зарегистрированного пользователя с таким email
        User existingUser = userService.findByEmail(user.getEmail());
        if (existingUser != null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Пользователь с таким email уже существует");
            return "redirect:/register";
        }

        // Сохраняем пользователя
        user.setRole(role);
        User savedUser = userService.saveUser(user);

        // Создаем профиль для пользователя
        Profile profile = new Profile();
        profile.setUser(savedUser); // Привязываем
        profileService.saveProfile(profile);

        // Сохраняем пользователя в сессии
        session.setAttribute("loggedInUser", savedUser);
        redirectAttributes.addFlashAttribute("successMessage", "Регистрация прошла успешно");
        return "redirect:/";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") User user, HttpSession session, RedirectAttributes redirectAttributes) {
        User existingUser = userService.findByEmail(user.getEmail());
        if (existingUser != null) {

            // Проверяем пароль
            boolean isPasswordCorrect = userService.checkPassword(existingUser, user.getPassword());
            if (isPasswordCorrect) {
                session.setAttribute("loggedInUser", existingUser);
                session.setAttribute("userRole", existingUser.getRole());
                System.out.println("Пользователь вошел: " + existingUser.getUsername());
                return "redirect:/";
            } else {
                System.out.println("Неправильный пароль для пользователя: " + user.getEmail());
            }
        } else {
            System.out.println("Пользователь с таким email не найден: " + user.getEmail());
        }

        redirectAttributes.addFlashAttribute("errorMessage", "Неправильный email или пароль");
        return "redirect:/";
    }
}
