package com.scheduler;

import com.service.UserMealListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CleanUpScheduler {

    private final UserMealListService userMealListService;

    @Autowired
    public CleanUpScheduler(UserMealListService userMealListService) {
        this.userMealListService = userMealListService;
    }
    @Scheduled(cron = "0 0 0 * * *")
    public void cleanUp() {
        userMealListService.deleteAll();
    }
}
