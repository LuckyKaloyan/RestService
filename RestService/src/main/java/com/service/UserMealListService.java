package com.service;
import com.model.UserMealList;
import com.repository.UserMealListRepository;
import com.web.dto.UserMealListRequest;
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
        this.userMealListRepository = userMealListRepository;
        this.mapper = mapper;
    }

    public void addMealToUser(UUID userId, UUID mealId) {
        UserMealList userMealList = userMealListRepository.findByUserId(userId)
                .orElseGet(() -> UserMealList.builder().userId(userId).mealsIds(new ArrayList<>()).build());
        userMealList.getMealsIds().add(mealId);
        userMealListRepository.save(userMealList);
    }

    public UserMealListRequest getUserMealList(UUID userId) {
        UserMealList userMealList = userMealListRepository.findByUserId(userId)
                .orElseGet(() -> UserMealList.builder().userId(userId).mealsIds(new ArrayList<>()).build());
        return mapper.toRequest(userMealList);
    }

    public void deleteMealFromUser(UUID userId, int mealIndex) {
        UserMealList userMealList = userMealListRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userMealList.getMealsIds().remove(mealIndex);
        userMealListRepository.save(userMealList);
    }

    @Transactional
    public void deleteAllMealsWithId(UUID mealId) {
        userMealListRepository.findAll().forEach(userMealList -> {
            userMealList.getMealsIds().removeIf(id -> id.equals(mealId));
            userMealListRepository.save(userMealList);
        });
    }
}