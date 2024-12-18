package com.example.fitclub.services;

import com.example.fitclub.models.Profile;
import com.example.fitclub.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    // Метод для поиска профиля по ID пользователя
    public Profile findProfileByUserId(Integer userId) {
        return profileRepository.findByUserId(userId);
    }

    // Метод для сохранения или обновления профиля
    public Profile saveProfile(Profile profile) {
        return profileRepository.save(profile);
    }
}
