package com.User.UserMicroservice.persistence.entity;

import com.User.UserMicroservice.enums.Language;
import com.User.UserMicroservice.enums.NotificationStatus;
import com.User.UserMicroservice.persistence.PreferenceRepository;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="preference")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Preference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private NotificationStatus notificationsEnabled = NotificationStatus.ACTIVE;

    @Enumerated(EnumType.STRING)
    private Language languagePreference = Language.ENGLISH;

    private  boolean darkModeEnabled = false;

    @Override
    public String toString() {
        return "Preference{" +
                "id=" + id +
                ", notificationsEnabled=" + notificationsEnabled +
                ", languagePreference='" + languagePreference + '\'' +
                ", darkModeEnabled=" + darkModeEnabled +
                '}';
    }
}
