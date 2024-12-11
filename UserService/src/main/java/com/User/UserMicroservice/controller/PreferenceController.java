package com.User.UserMicroservice.controller;

import com.User.UserMicroservice.dto.ChangeLanguage;
import com.User.UserMicroservice.enums.NotificationStatus;
import com.User.UserMicroservice.persistence.entity.Preference;
import com.User.UserMicroservice.service.PreferenceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/preferences")
@AllArgsConstructor
public class PreferenceController {

    private final PreferenceService preferenceService;


    @GetMapping("/{id}")
    public ResponseEntity<Preference> getPreferenceById(@PathVariable Long id) {
        Preference preference = preferenceService.getPreferenceById(id);
        return ResponseEntity.ok(preference);
    }


    @PutMapping("/updateLanguage")
    public ResponseEntity<Preference> updatePreferenceLanguage(@RequestBody ChangeLanguage changeLanguage) {
        String updatedPreference = preferenceService.changeLanguage(changeLanguage);
        return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
    }

    @PutMapping("/updateNotification/{id}/{notificationEnable}")
    public ResponseEntity<Preference> updateNotification(@PathVariable Long id,@PathVariable NotificationStatus notificationEnable) {
        boolean updatedPreference = preferenceService.changeNotification(id,notificationEnable);
        return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
    }


}
