package com.repository;

import com.model.UserMealList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserMealListRepository extends JpaRepository<UserMealList, UUID> {
    Optional<UserMealList> findByUserId(UUID userId);
}
