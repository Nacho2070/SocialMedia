package com.User.UserMicroservice.service;

import com.User.UserMicroservice.dto.ChangeLanguage;
import com.User.UserMicroservice.enums.NotificationStatus;
import com.User.UserMicroservice.persistence.PreferenceRepository;
import com.User.UserMicroservice.persistence.UserRepository;
import com.User.UserMicroservice.persistence.entity.Preference;
import com.User.UserMicroservice.persistence.entity.User;
import com.User.UserMicroservice.utils.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PreferenceService extends GenericService<Preference,Long> {

    private final UserRepository userRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public Preference getPreferenceById(Long id) {
       User user = userRepository.findById(id).orElseThrow(RuntimeException::new);
       return user.getPreferences();
    }

    public String changeLanguage(ChangeLanguage changeLanguage) {
        User user = userRepository.findById(changeLanguage.userId()).orElseThrow(RuntimeException::new);
        user.getPreferences().setLanguagePreference(changeLanguage.language());
        return "Language changed!";
    }

    public Boolean changeNotification(Long id, NotificationStatus notificationEnable) {
        if (notificationEnable.equals(NotificationStatus.ACTIVE)) {
            User user = userRepository.findById(id).orElseThrow(RuntimeException::new);
            user.getPreferences().setNotificationsEnabled(notificationEnable);
            kafkaTemplate.send("NotificationsStatus", notificationEnable.toString());

            return true;
        }
        return false;
    }
}
