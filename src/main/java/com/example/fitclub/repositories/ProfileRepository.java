package com.example.fitclub.repositories;

import com.example.fitclub.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    Profile findByUserId(Integer userId);  // Поиск профиля по ID пользователя
}