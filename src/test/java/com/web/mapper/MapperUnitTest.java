package com.web.mapper;


import com.model.UserMealList;
import com.web.dto.UserMealListRequest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MapperUnitTest {

    private final Mapper mapper = new Mapper();

    @Test
    void toRequestMapAllFieldsCorrectly() {
        UUID userId = UUID.randomUUID();
        UserMealList entity = UserMealList.builder()
                .userId(userId)
                .mealsIds(Arrays.asList(UUID.randomUUID(), UUID.randomUUID()))
                .build();
        UserMealListRequest request = mapper.toRequest(entity);
        assertNotNull(request);
        assertEquals(userId, request.getUserId());
        assertEquals(2, request.getMealsIds().size());
        assertEquals(entity.getMealsIds(), request.getMealsIds());
    }

    @Test
    void toRequestHandleEmptyMealsList() {
        UUID userId = UUID.randomUUID();
        UserMealList entity = UserMealList.builder()
                .userId(userId)
                .mealsIds(Collections.emptyList())
                .build();
        UserMealListRequest request = mapper.toRequest(entity);
        assertNotNull(request);
        assertEquals(userId, request.getUserId());
        assertTrue(request.getMealsIds().isEmpty());
    }

    @Test
    void toRequestHandleNullInput() {
        assertThrows(NullPointerException.class, () -> mapper.toRequest(null));
    }

    @Test
    void toEntityMapAllFieldsCorrectly() {
        UUID userId = UUID.randomUUID();
        UserMealListRequest request = UserMealListRequest.builder()
                .userId(userId)
                .mealsIds(Arrays.asList(UUID.randomUUID(), UUID.randomUUID()))
                .build();
        UserMealList entity = mapper.toEntity(request);
        assertNotNull(entity);
        assertEquals(userId, entity.getUserId());
        assertEquals(2, entity.getMealsIds().size());
        assertEquals(request.getMealsIds(), entity.getMealsIds());
    }

    @Test
    void toEntityHandleEmptyMealsList() {
        UUID userId = UUID.randomUUID();
        UserMealListRequest request = UserMealListRequest.builder()
                .userId(userId)
                .mealsIds(Collections.emptyList())
                .build();

        UserMealList entity = mapper.toEntity(request);
        assertNotNull(entity);
        assertEquals(userId, entity.getUserId());
        assertTrue(entity.getMealsIds().isEmpty());
    }

    @Test
    void toEntityHandleNullInput() {
        assertThrows(NullPointerException.class, () -> mapper.toEntity(null));
    }

    @Test
    void bidirectionalMappingBeConsistent() {
        UUID userId = UUID.randomUUID();
        UserMealList originalEntity = UserMealList.builder()
                .userId(userId)
                .mealsIds(Arrays.asList(UUID.randomUUID(), UUID.randomUUID()))
                .build();

        UserMealListRequest request = mapper.toRequest(originalEntity);
        UserMealList mappedEntity = mapper.toEntity(request);
        assertEquals(originalEntity.getUserId(), mappedEntity.getUserId());
        assertEquals(originalEntity.getMealsIds(), mappedEntity.getMealsIds());
    }

}