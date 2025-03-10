package com.web;

import com.model.UserMealList;
import com.service.UserMealListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/meal_tracker")
public class MealTrackerController {

    private final UserMealListService userMealListService;

    @Autowired
    public MealTrackerController(UserMealListService userMealListService) {
        this.userMealListService = userMealListService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Void> addMealToUser(@PathVariable UUID userId, @RequestParam UUID mealId) {
        userMealListService.addMealToUser(userId, mealId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "http://localhost:8080/calculator/did_you_eat_enough_today");
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @GetMapping("/{userId}")
    public UserMealList getUserMealList(@PathVariable UUID userId) {
        return userMealListService.getUserMealList(userId);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteMealFromUser(@PathVariable UUID userId, @RequestParam int mealIndex) {
        userMealListService.deleteMealFromUser(userId, mealIndex);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "http://localhost:8080/calculator/did_you_eat_enough_today");
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @DeleteMapping("/removeMealFromAllUsers/{mealId}")
    public ResponseEntity<Void> removeMealFromAllUsers(@PathVariable UUID mealId) {
        userMealListService.deleteAllMealsWithId(mealId);
        return ResponseEntity.noContent().build();
    }
}