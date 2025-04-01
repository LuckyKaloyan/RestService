package com.service;

import com.model.UserMealList;
import com.repository.UserMealListRepository;
import com.web.dto.UserMealListRequest;
import com.exception.NotFoundException;
import com.web.mapper.Mapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class UserMealListService {

    private final UserMealListRepository userMealListRepository;
    private final Mapper mapper;

    @Autowired
    public UserMealListService(UserMealListRepository userMealListRepository, Mapper mapper) {
        if (userMealListRepository == null) {
            throw new IllegalArgumentException("UserMealListRepository cannot be null");
        }
        if (mapper == null) {
            throw new IllegalArgumentException("Mapper cannot be null");
        }
        this.userMealListRepository = userMealListRepository;
        this.mapper = mapper;
    }

    public void addMealToUser(UUID userId, UUID mealId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if (mealId == null) {
            throw new IllegalArgumentException("Meal ID cannot be null");
        }

        UserMealList userMealList = userMealListRepository.findByUserId(userId)
                .orElseGet(() -> UserMealList.builder()
                        .userId(userId)
                        .mealsIds(new ArrayList<>())
                        .build());

        userMealList.getMealsIds().add(mealId);
        userMealListRepository.save(userMealList);
    }

    public UserMealListRequest getUserMealList(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        UserMealList userMealList = userMealListRepository.findByUserId(userId)
                .orElseGet(() -> UserMealList.builder()
                        .userId(userId)
                        .mealsIds(new ArrayList<>())
                        .build());

        return mapper.toRequest(userMealList);
    }

    public void deleteMealFromUser(UUID userId, int mealIndex) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if (mealIndex < 0) {
            throw new IllegalArgumentException("Meal index cannot be negative");
        }

        UserMealList userMealList = userMealListRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("User with ID " + userId + " not found"));

        if (mealIndex >= userMealList.getMealsIds().size()) {
            throw new IndexOutOfBoundsException("Meal index " + mealIndex + " is out of bounds");
        }

        userMealList.getMealsIds().remove(mealIndex);
        userMealListRepository.save(userMealList);
    }

    @Transactional
    public void deleteAllMealsWithId(UUID mealId) {
        if (mealId == null) {
            throw new IllegalArgumentException("Meal ID cannot be null");
        }

        userMealListRepository.findAll().forEach(userMealList -> {
            if (userMealList.getMealsIds() != null) {
                userMealList.getMealsIds().removeIf(id -> mealId.equals(id));
                userMealListRepository.save(userMealList);
            }
        });
    }

    public void deleteAll(){
        userMealListRepository.deleteAll();
    }
}