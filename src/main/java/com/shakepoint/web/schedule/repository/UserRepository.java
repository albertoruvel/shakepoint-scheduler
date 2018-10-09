package com.shakepoint.web.schedule.repository;

import com.shakepoint.web.schedule.data.entity.UserProfile;

import java.util.List;

public interface UserRepository {
    List<UserProfile> usersWithBirthDates();
}
