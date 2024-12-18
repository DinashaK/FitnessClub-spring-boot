package com.example.fitclub.services;

import com.example.fitclub.models.Schedule;
import com.example.fitclub.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    // Получить все тренировки
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    // Сохранить или обновить тренировку
    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    // Получить тренировку по ID
    public Schedule getScheduleById(Long id) {
        return scheduleRepository.findById(id).orElse(null);  // Возвращаем пустой объект, если не найдено
    }

    // Удалить тренировку по ID
    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }

    // Поиск тренировок по дню, времени и названию
    public List<Schedule> searchSchedules(String day, String time, String workoutName) {
        return scheduleRepository.searchSchedules(day, time, workoutName);
    }

    public long getTotalSchedules() {
        return scheduleRepository.count();
    }

    public double getAverageSchedulesPerDay() {
        long totalSchedules = scheduleRepository.count();
        long distinctDays = scheduleRepository.countDistinctByDay();
        return (double) totalSchedules / distinctDays;
    }

    public Map<String, Long> getWorkoutsByDay() {
        List<Object[]> results = scheduleRepository.countWorkoutsByDay();

        Map<String, Long> workoutsByDay = new HashMap<>();

        for (Object[] result : results) {
            String day = (String) result[0];
            Long count = (Long) result[1];
            workoutsByDay.put(day, count);
        }
        return workoutsByDay;
    }
}
