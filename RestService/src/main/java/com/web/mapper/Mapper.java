package com.web.mapper;
import com.dto.UserMealListRequest;
import com.model.UserMealList;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public UserMealListRequest toRequest(UserMealList userMealList) {
        return UserMealListRequest.builder()
                .userId(userMealList.getUserId())
                .mealsIds(userMealList.getMealsIds())
                .build();
    }
    public UserMealList toEntity(UserMealListRequest userMealListDto) {
        return UserMealList.builder()
                .userId(userMealListDto.getUserId())
                .mealsIds(userMealListDto.getMealsIds())
                .build();
    }
}