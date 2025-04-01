package com.service;

import com.model.UserMealList;
import com.repository.UserMealListRepository;
import com.web.dto.UserMealListRequest;
import com.web.mapper.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserMealListServiceUnitTest {

    @Mock
    private UserMealListRepository userMealListRepository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private UserMealListService userMealListService;

    private UUID userId;
    private UUID mealId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        mealId = UUID.randomUUID();
    }

    @Test
    void addMealToUser_shouldAddMealToExistingList() {
        UserMealList existingList = new UserMealList();
        existingList.setUserId(userId);
        existingList.setMealsIds(new ArrayList<>(Arrays.asList(UUID.randomUUID())));
        when(userMealListRepository.findByUserId(userId)).thenReturn(Optional.of(existingList));
        userMealListService.addMealToUser(userId, mealId);
        assertEquals(2, existingList.getMealsIds().size());
        assertTrue(existingList.getMealsIds().contains(mealId));
    }

    @Test
    void addMealToUser_shouldCreateNewListWhenNotExists() {
        when(userMealListRepository.findByUserId(userId)).thenReturn(Optional.empty());
        userMealListService.addMealToUser(userId, mealId);
        assertDoesNotThrow(() -> userMealListService.addMealToUser(userId, mealId));
    }

    @Test
    void getUserMealList_shouldReturnListWithMeals() {
        UserMealList existingList = new UserMealList();
        existingList.setUserId(userId);
        existingList.setMealsIds(new ArrayList<>(Arrays.asList(mealId)));
        UserMealListRequest expectedRequest = new UserMealListRequest();
        expectedRequest.setUserId(userId);
        expectedRequest.setMealsIds(existingList.getMealsIds());
        when(userMealListRepository.findByUserId(userId)).thenReturn(Optional.of(existingList));
        when(mapper.toRequest(existingList)).thenReturn(expectedRequest);
        UserMealListRequest result = userMealListService.getUserMealList(userId);
        assertEquals(userId, result.getUserId());
        assertEquals(1, result.getMealsIds().size());
        assertEquals(mealId, result.getMealsIds().get(0));
    }

    @Test
    void deleteMealFromUser_shouldRemoveMeal() {
        UUID mealId2 = UUID.randomUUID();
        UserMealList existingList = new UserMealList();
        existingList.setUserId(userId);
        existingList.setMealsIds(new ArrayList<>(Arrays.asList(mealId, mealId2)));
        when(userMealListRepository.findByUserId(userId)).thenReturn(Optional.of(existingList));
        userMealListService.deleteMealFromUser(userId, 0);
        assertEquals(1, existingList.getMealsIds().size());
        assertEquals(mealId2, existingList.getMealsIds().get(0));
    }
    @Test
    void deleteAllMealsWithId_shouldRemoveSpecificMeal() {
        UUID mealId2 = UUID.randomUUID();
        UserMealList list1 = new UserMealList();
        list1.setUserId(UUID.randomUUID());
        list1.setMealsIds(new ArrayList<>(Arrays.asList(mealId, mealId2, mealId)));
        UserMealList list2 = new UserMealList();
        list2.setUserId(UUID.randomUUID());
        list2.setMealsIds(new ArrayList<>(Arrays.asList(mealId2)));
        when(userMealListRepository.findAll()).thenReturn(Arrays.asList(list1, list2));
        userMealListService.deleteAllMealsWithId(mealId);
        assertEquals(1, list1.getMealsIds().size());
        assertFalse(list1.getMealsIds().contains(mealId));
        assertEquals(1, list2.getMealsIds().size());
    }

    @Test
    void inputValidation_shouldThrowForNullInputs() {
        assertThrows(IllegalArgumentException.class, () -> userMealListService.addMealToUser(null, mealId));
        assertThrows(IllegalArgumentException.class, () -> userMealListService.addMealToUser(userId, null));
        assertThrows(IllegalArgumentException.class, () -> userMealListService.getUserMealList(null));
        assertThrows(IllegalArgumentException.class, () -> userMealListService.deleteMealFromUser(null, 0));
        assertThrows(IllegalArgumentException.class, () -> userMealListService.deleteAllMealsWithId(null));
    }
}