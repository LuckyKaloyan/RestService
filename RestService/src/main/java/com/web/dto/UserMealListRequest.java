package com.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMealListRequest {
    private UUID userId;
    private List<UUID> mealsIds;
}