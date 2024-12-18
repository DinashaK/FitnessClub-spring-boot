package com.example.fitclub.controllers;

import com.example.fitclub.models.Profile;
import com.example.fitclub.models.User;
import com.example.fitclub.services.ProfileService;
import com.example.fitclub.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserService userService;

    // Страница отображения профиля
    @GetMapping("/profile")
    public String viewProfile(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser != null) {
            Profile profile = profileService.findProfileByUserId(loggedInUser.getId());
            if (profile != null) {
                model.addAttribute("profile", profile);
                model.addAttribute("role", loggedInUser.getRole());
                model.addAttribute("loggedInUser", loggedInUser);
                return "profile";
            } else {
                return "redirect:/profile/edit";
            }
        }

        return "redirect:/";
    }

    // Страница редактирования профиля
    @GetMapping("/profile/edit")
    public String editProfile(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser != null) {
            Profile profile = profileService.findProfileByUserId(loggedInUser.getId());
            model.addAttribute("profile", profile);
            model.addAttribute("role", loggedInUser.getRole());
            model.addAttribute("loggedInUser", loggedInUser);
            return "edit_profile";
        }

        return "redirect:/";
    }

    // Обработчик сохранения изменений профиля
    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam("firstName") String firstName,
                                @RequestParam("lastName") String lastName,
                                @RequestParam("email") String email,
                                @RequestParam("phoneNumber") String phoneNumber,
                                @RequestParam("address") String address,
                                @RequestParam("bio") String bio,
                                HttpSession session,
                                Model model) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser != null) {
            Profile profile = profileService.findProfileByUserId(loggedInUser.getId());
            if (profile != null) {
                if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
                    model.addAttribute("errorMessage", "Поля имя, фамилия и email не могут быть пустыми.");
                    model.addAttribute("profile", profile);
                    return "edit_profile";
                }

                profile.setFirstName(firstName);
                profile.setLastName(lastName);
                profile.setEmail(email);
                profile.setPhoneNumber(phoneNumber);
                profile.setAddress(address);
                profile.setBio(bio);
                profileService.saveProfile(profile);  // Сохраняем обновленный профиль
                return "redirect:/profile";
            } else {
                return "redirect:/profile/edit";
            }
        }

        return "redirect:/";
    }

    // Страница управления ролями пользователей
    @GetMapping("/profile/manage_roles")
    public String manageRoles(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser != null) {
            if ("ADMIN".equals(loggedInUser.getRole())) {
                List<User> users = userService.getAllUsers();
                model.addAttribute("users", users);
                model.addAttribute("loggedInUser", loggedInUser);
                model.addAttribute("role", loggedInUser.getRole());
                return "manage_roles";
            } else {
                return "redirect:/profile";
            }
        }

        return "redirect:/";
    }

    // Обработчик обновления роли пользователя
    @PostMapping("/profile/manage_roles/update-role")
    public String updateUserRole(@RequestParam("userId") Integer userId,
                                 @RequestParam("role") String role,
                                 HttpSession session) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !loggedInUser.getRole().equals("ADMIN")) {
            return "redirect:/profile";
        }

        User user = userService.getUserById(userId);
        if (user != null) {
            user.setRole(role); // Обновляем роль пользователя
            userService.saveUser(user);
        }

        return "redirect:/profile/manage_roles";
    }
}
