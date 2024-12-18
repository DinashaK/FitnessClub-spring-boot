package com.example.fitclub.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String day;
    private String time;

    private String workoutName;
    private String description;
    private String trainerName;

    @Override
    public String toString() {
        return "Schedule[id=" + id + ", day=" + day + ", time=" + time + ", workoutName=" + workoutName + ", trainerName=" + trainerName + "]";
    }
}
