package com.example.fitclub.controllers;

import com.example.fitclub.models.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppController {

    // Главная страница
    @RequestMapping("/")
    public String viewHomePage(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser != null) {
            model.addAttribute("loggedInUser", loggedInUser);
            model.addAttribute("userRole", loggedInUser.getRole());
        } else {
            model.addAttribute("loggedInUser", null);
        }

        return "index";
    }

    // Обработчик выхода
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();  // Уничтожаем сессию пользователя
        return "redirect:/";   // Перенаправляем на главную страницу
    }
}
