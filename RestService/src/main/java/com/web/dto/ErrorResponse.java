package com.web.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Setter
@Getter
public class ErrorResponse {

    private int status;
    private String message;
    private LocalTime timestamp;


    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalTime.now();
    }
}
