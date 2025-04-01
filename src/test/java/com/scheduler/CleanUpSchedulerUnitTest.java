package com.scheduler;

import com.service.UserMealListService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CleanUpSchedulerUnitTest {
    @Mock
    private UserMealListService userMealListService;

    @InjectMocks
    private CleanUpScheduler cleanUpScheduler;

    @Test
    void cleanUp_shouldCallDeleteAllOnUserMealListService() {
        cleanUpScheduler.cleanUp();
        verify(userMealListService).deleteAll();
    }
}
