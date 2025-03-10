package com.service;

import com.model.UserMealList;
import com.repository.UserMealListRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class UserMealListService {

    private final UserMealListRepository userMealListRepository;

    @Autowired
    public UserMealListService(UserMealListRepository userMealListRepository) {
        this.userMealListRepository = userMealListRepository;
    }

    public UserMealList addMealToUser(UUID userId, UUID mealId) {

        if (userMealListRepository.findByUserId(userId).isPresent()) {
            UserMealList userMealList = userMealListRepository.findByUserId(userId).get();
            userMealList.getMealsIds().add(mealId);
            return userMealListRepository.save(userMealList);
        }

        UserMealList newUserMealList = UserMealList.builder()
                .userId(userId)
                .mealsIds(List.of(mealId))
                .build();
        return userMealListRepository.save(newUserMealList);
    }

    public UserMealList getUserMealList(UUID userId) {
        if(userMealListRepository.findByUserId(userId).isEmpty()){
            return UserMealList.builder()
                    .userId(userId)
                    .mealsIds(List.of())
                    .build();
        }
        return userMealListRepository.findByUserId(userId).get();
    }

    public void deleteMealFromUser(UUID userId, int mealIndex) {
        getUserMealList(userId).getMealsIds().remove(mealIndex);
        userMealListRepository.save(getUserMealList(userId));
    }

    @Transactional
    public void deleteAllMealsWithId(UUID mealId) {
        userMealListRepository.findAll().forEach(userMealList -> {
            userMealList.getMealsIds().removeIf(id -> id.equals(mealId));
            userMealListRepository.save(userMealList);
        });
    }
}
