package com.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewProductRequest {

    private String name;
    private int quantity;
}
