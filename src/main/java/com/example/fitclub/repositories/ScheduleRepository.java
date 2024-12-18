package com.example.fitclub.repositories;

import com.example.fitclub.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT s FROM Schedule s WHERE " +
            "(:day IS NULL OR LOWER(s.day) LIKE LOWER(CONCAT('%', :day, '%'))) " +
            "AND (:time IS NULL OR LOWER(s.time) LIKE LOWER(CONCAT('%', :time, '%'))) " +
            "AND (:workoutName IS NULL OR LOWER(s.workoutName) LIKE LOWER(CONCAT('%', :workoutName, '%')))")
    List<Schedule> searchSchedules(
            @Param("day") String day,
            @Param("time") String time,
            @Param("workoutName") String workoutName
    );

    @Query("SELECT COUNT(DISTINCT s.day) FROM Schedule s")
    long countDistinctByDay();

    @Query("SELECT s.day, COUNT(s) FROM Schedule s GROUP BY s.day")
    List<Object[]> countWorkoutsByDay();
}
