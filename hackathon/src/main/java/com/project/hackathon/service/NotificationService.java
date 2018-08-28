package com.project.hackathon.service;

import com.project.hackathon.dto.ProgressDto;

public interface NotificationService {

    void notifyToAllSession(ProgressDto notification);

    void progress(String step);
}
