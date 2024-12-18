package com.example.fitclub.controllers;

import com.example.fitclub.ScheduleData;
import com.example.fitclub.models.Schedule;
import com.example.fitclub.services.ScheduleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    // Главная страница расписания
    @GetMapping
    public String getSchedule(Model model, HttpSession session) throws JsonProcessingException {

        Object loggedInUser = session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            model.addAttribute("loggedInUser", loggedInUser);
        }

        long totalSchedules = scheduleService.getTotalSchedules();
        double averageSchedulesPerDay = scheduleService.getAverageSchedulesPerDay();

        Map<String, Long> workoutsByDay = scheduleService.getWorkoutsByDay();
        ObjectMapper objectMapper = new ObjectMapper();
        String workoutsByDayJson = objectMapper.writeValueAsString(workoutsByDay);

        model.addAttribute("totalSchedules", totalSchedules);
        model.addAttribute("averageSchedulesPerDay", averageSchedulesPerDay);
        model.addAttribute("workoutsByDayJson", workoutsByDayJson);
        model.addAttribute("schedule", scheduleService.getAllSchedules());
        return "schedule";
    }

    // Добавить новую тренировку
    @GetMapping("/add")
    public String addScheduleForm(Model model, HttpSession session) {
        String role = (String) session.getAttribute("userRole");

        if (role == null || (!role.equals("MANAGER") && !role.equals("ADMIN"))) {
            return "redirect:/schedule";
        }

        // Создаем пустой объект Schedule для привязки данных к форме
        model.addAttribute("schedule", new Schedule());
        return "add_schedule";
    }

    // Обработать форму добавления тренировки
    @PostMapping("/add")
    public String addSchedule(@ModelAttribute Schedule schedule, HttpSession session) {
        String role = (String) session.getAttribute("userRole");

        if (role == null || (!role.equals("MANAGER") && !role.equals("ADMIN"))) {
            return "redirect:/schedule";
        }

        scheduleService.saveSchedule(schedule);
        return "redirect:/schedule";
    }

    // Редактирование тренировки
    @GetMapping("/edit/{id}")
    public String editScheduleForm(@PathVariable Long id, Model model, HttpSession session) {
        String role = (String) session.getAttribute("userRole");

        // Проверка роли пользователя
        if (role == null || (!role.equals("MANAGER") && !role.equals("ADMIN"))) {
            return "redirect:/schedule";
        }

        Schedule schedule = scheduleService.getScheduleById(id);  // Получаем тренировку по ID
        model.addAttribute("schedule", schedule);  // Добавляем в модель
        return "edit_schedule";
    }

    @PostMapping("/edit/{id}")
    public String editSchedule(@PathVariable Long id, @RequestParam String workoutName, @RequestParam String trainerName,
                               @RequestParam String day, @RequestParam String time, HttpSession session) {

        String role = (String) session.getAttribute("userRole");

        // Проверка роли пользователя
        if (role == null || (!role.equals("MANAGER") && !role.equals("ADMIN"))) {
            return "redirect:/schedule";
        }

        Schedule schedule = scheduleService.getScheduleById(id);  // Получаем тренировку по ID
        schedule.setWorkoutName(workoutName);
        schedule.setTrainerName(trainerName);
        schedule.setDay(day);
        schedule.setTime(time);
        scheduleService.saveSchedule(schedule);

        return "redirect:/schedule";
    }

    // Удаление тренировки
    @GetMapping("/delete/{id}")
    public String deleteSchedule(@PathVariable Long id, HttpSession session) {
        String role = (String) session.getAttribute("userRole");

        if (role == null || (!role.equals("MANAGER") && !role.equals("ADMIN"))) {
            return "redirect:/schedule";
        }

        scheduleService.deleteSchedule(id);

        return "redirect:/schedule";
    }

    @GetMapping("/search")
    public String searchSchedule(
            @RequestParam(value = "day", required = false) String day,
            @RequestParam(value = "time", required = false) String time,
            @RequestParam(value = "workoutName", required = false) String workoutName,
            Model model) {

        List<Schedule> results = scheduleService.searchSchedules(day, time, workoutName);
        model.addAttribute("schedule", results);
        return "schedule";
    }

    // Получение данных о расписании для API
    @GetMapping("/api/scheduleData")
    @ResponseBody
    public List<ScheduleData> getScheduleData() {
        // Получаем данные о расписании
        Map<String, Long> workoutsByDay = scheduleService.getWorkoutsByDay();

        // Преобразуем Map в список объектов ScheduleData
        List<ScheduleData> scheduleDataList = new ArrayList<>();
        for (Map.Entry<String, Long> entry : workoutsByDay.entrySet()) {
            String day = entry.getKey();
            long count = entry.getValue();
            scheduleDataList.add(new ScheduleData(day, (int) count));
        }

        return scheduleDataList;
    }
}
