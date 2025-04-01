package com.web.mapper;
import com.model.UserMealList;
import com.web.dto.UserMealListRequest;
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