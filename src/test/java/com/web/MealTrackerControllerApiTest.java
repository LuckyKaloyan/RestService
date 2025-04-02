package com.web;

import com.service.UserMealListService;
import com.web.dto.UserMealListRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MealTrackerController.class)
public class MealTrackerControllerApiTest {

    @MockitoBean
    private UserMealListService userMealListService;

    @Autowired
    private MockMvc mockMvc;

    private final UUID testUserId = UUID.randomUUID();
    private final UUID testMealId = UUID.randomUUID();
    private final UserMealListRequest testMealListRequest = new UserMealListRequest();

    @Test
    void addMealToUser_ShouldReturnFoundStatusWithLocationHeader() throws Exception {
        mockMvc.perform(post("/api/v1/meal_tracker/{userId}", testUserId)
                        .param("mealId", testMealId.toString()))
                .andExpect(status().isFound())
                .andExpect(header().string("Location",
                        "http://localhost:8080/calculator/did_you_eat_enough_today"));

        verify(userMealListService).addMealToUser(testUserId, testMealId);
    }

    @Test
    void getUserMealList_ShouldReturnUserMealList() throws Exception {
        when(userMealListService.getUserMealList(testUserId)).thenReturn(testMealListRequest);

        mockMvc.perform(get("/api/v1/meal_tracker/{userId}", testUserId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(userMealListService).getUserMealList(testUserId);
    }

    @Test
    void deleteMealFromUser_ShouldReturnFoundStatusWithLocationHeader() throws Exception {
        int mealIndex = 1;

        mockMvc.perform(delete("/api/v1/meal_tracker/{userId}", testUserId)
                        .param("mealIndex", String.valueOf(mealIndex)))
                .andExpect(status().isFound())
                .andExpect(header().string("Location",
                        "http://localhost:8080/calculator/did_you_eat_enough_today"));

        verify(userMealListService).deleteMealFromUser(testUserId, mealIndex);
    }

    @Test
    void removeMealFromAllUsers_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/meal_tracker/removeMealFromAllUsers/{mealId}", testMealId))
                .andExpect(status().isNoContent());

        verify(userMealListService).deleteAllMealsWithId(testMealId);
    }

    @Test
    void addMealToUser_ShouldReturnBadRequestWhenMealIdMissing() throws Exception {
        mockMvc.perform(post("/api/v1/meal_tracker/{userId}", testUserId))
                .andExpect(status().isBadGateway());

        verify(userMealListService, never()).addMealToUser(any(), any());
    }

    @Test
    void deleteMealFromUser_ShouldReturnBadRequestWhenMealIndexMissing() throws Exception {
        mockMvc.perform(delete("/api/v1/meal_tracker/{userId}", testUserId))
                .andExpect(status().isBadGateway());

        verify(userMealListService, never()).deleteMealFromUser(any(), anyInt());
    }

    @Test
    void deleteMealFromUser_ShouldReturnBadRequestWhenMealIndexInvalid() throws Exception {
        mockMvc.perform(delete("/api/v1/meal_tracker/{userId}", testUserId)
                        .param("mealIndex", "invalid"))
                .andExpect(status().isBadGateway());

        verify(userMealListService, never()).deleteMealFromUser(any(), anyInt());
    }
}